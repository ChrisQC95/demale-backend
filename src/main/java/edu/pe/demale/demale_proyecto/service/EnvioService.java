package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.EnvioCreacionDto;
import edu.pe.demale.demale_proyecto.dto.EnvioListadoDto;
import edu.pe.demale.demale_proyecto.dto.EnvioUpdateDto;
import edu.pe.demale.demale_proyecto.models.Productos; // Asegúrate de importar la entidad DetalleEnvio
import edu.pe.demale.demale_proyecto.models.DetalleEnvio;
import edu.pe.demale.demale_proyecto.models.Envio;
import edu.pe.demale.demale_proyecto.repositories.EnvioRepository;
import edu.pe.demale.demale_proyecto.repositories.DetalleEnvioRepository;
import edu.pe.demale.demale_proyecto.repositories.IProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante para la transacción

import java.sql.Date; // Para convertir String a java.sql.Date
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final DetalleEnvioRepository detalleEnvioRepository;
    private final IProductosRepository productoRepository;

    // ID del estado "En Tránsito"
    private static final int ESTADO_EN_TRANSITO_ID = 2;
    private static final int ESTADO_EN_ALMACEN_ID = 1;

    @Autowired
    public EnvioService(EnvioRepository envioRepository,
            DetalleEnvioRepository detalleEnvioRepository,
            IProductosRepository productoRepository) {
        this.envioRepository = envioRepository;
        this.detalleEnvioRepository = detalleEnvioRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Envio crearNuevoEnvio(EnvioCreacionDto envioDto) {
        // 1. Crear e insertar el Envío principal
        Envio nuevoEnvio = new Envio();
        nuevoEnvio.setIdConductor(envioDto.getIdConductor());
        nuevoEnvio.setIdVehiculo(envioDto.getIdVehiculo());
        nuevoEnvio.setIdRuta(envioDto.getIdRuta());
        nuevoEnvio.setFechSalida(Date.valueOf(envioDto.getFechSalida()));
        nuevoEnvio.setObservacion(envioDto.getObservacion());
        nuevoEnvio.setIdEstadoEnvio(ESTADO_EN_TRANSITO_ID);
        nuevoEnvio.setFechLlegada(null);
        nuevoEnvio.setIdPuntoAcopio(envioDto.getIdAcopio());
        nuevoEnvio.setIdDistrito(envioDto.getIdDestino());

        Envio envioGuardado = envioRepository.save(nuevoEnvio);

        if (envioGuardado.getIdEnvio() == null) {
            throw new RuntimeException("No se pudo obtener el IdEnvio generado después de la inserción.");
        }

        // 2. Iterar sobre los productos seleccionados para insertar en DetalleEnvios y
        // actualizar su estado
        if (envioDto.getIdProductosSeleccionados() != null && !envioDto.getIdProductosSeleccionados().isEmpty()) {
            for (Integer idProducto : envioDto.getIdProductosSeleccionados()) {
                // Obtener el objeto Producto completo
                // Es crucial que el producto exista para evitar NullPointerException o
                // ConstraintViolation
                Productos producto = productoRepository.findById(idProducto)
                        .orElseThrow(() -> new RuntimeException("Producto con ID " + idProducto + " no encontrado."));

                // Crear la entidad DetalleEnvio
                DetalleEnvio detalleEnvio = new DetalleEnvio();
                // Establecer las entidades relacionadas. Esto es clave.
                detalleEnvio.setEnvio(envioGuardado);
                detalleEnvio.setProducto(producto);
                detalleEnvioRepository.save(detalleEnvio);
                productoRepository.actualizarEstadoProducto(idProducto, ESTADO_EN_TRANSITO_ID);
            }
        } else {
            System.out.println("Advertencia: Se creó un envío sin productos seleccionados.");
        }

        return envioGuardado;
    }

    public List<EnvioListadoDto> obtenerTodosLosEnvios() {
        List<Envio> envios = envioRepository.findAll();
        return envios.stream()
                .map(this::mapToEnvioListadoDto)
                .collect(Collectors.toList());
    }

    private EnvioListadoDto mapToEnvioListadoDto(Envio envio) {
        EnvioListadoDto dto = new EnvioListadoDto();
        dto.setIdEnvio(envio.getIdEnvio());
        dto.setFechSalida(envio.getFechSalida());
        dto.setFechLlegada(envio.getFechLlegada());
        dto.setObservacion(envio.getObservacion());
        if (envio.getConductor() != null) {
            dto.setConductorNombreCompleto(envio.getConductor().getPersona().getNombres() + " "
                    + envio.getConductor().getPersona().getApellidos());
        } else {
            dto.setConductorNombreCompleto("N/A");
        }

        if (envio.getVehiculo() != null) {
            dto.setVehiculoDescripcion(envio.getVehiculo().getMarca() + " - " + envio.getVehiculo().getModelo() + " ("
                    + envio.getVehiculo().getPlaca() + ")");
        } else {
            dto.setVehiculoDescripcion("N/A");
        }

        if (envio.getRuta() != null) {
            dto.setRutaDescripcion(envio.getRuta().getSerialRuta() + " - " + envio.getRuta().getNombreRuta());
        } else {
            dto.setRutaDescripcion("N/A");
        }

        if (envio.getEstadoEnvio() != null) {
            dto.setEstadoEnvioNombre(envio.getEstadoEnvio().getEstado());
        } else {
            dto.setEstadoEnvioNombre("N/A");
        }

        if (envio.getPuntoAcopio() != null) {
            dto.setPuntoAcopioNombre(envio.getPuntoAcopio().getNombreAcopio());
        } else {
            dto.setPuntoAcopioNombre("N/A");
        }

        if (envio.getDistrito() != null) {
            dto.setDistritoDestinoNombre(envio.getDistrito().getNombreDistrito());
        } else {
            dto.setDistritoDestinoNombre("N/A");
        }

        return dto;
    }

    @Transactional
    public EnvioListadoDto actualizarEnvio(EnvioUpdateDto envioDto) {
        Envio envioExistente = envioRepository.findById(envioDto.getIdEnvio())
                .orElseThrow(() -> new RuntimeException(
                        "Envío con ID " + envioDto.getIdEnvio() + " no encontrado para actualizar."));
        if (envioDto.getIdConductor() != null) {
            envioExistente.setIdConductor(envioDto.getIdConductor());
        }
        if (envioDto.getIdVehiculo() != null) {
            envioExistente.setIdVehiculo(envioDto.getIdVehiculo());
        }
        if (envioDto.getIdRuta() != null) {
            envioExistente.setIdRuta(envioDto.getIdRuta());
        }
        if (envioDto.getIdEstadoEnvio() != null) {
            envioExistente.setIdEstadoEnvio(envioDto.getIdEstadoEnvio());
        }
        if (envioDto.getIdPuntoAcopio() != null) {
            envioExistente.setIdPuntoAcopio(envioDto.getIdPuntoAcopio());
        }
        if (envioDto.getIdDistrito() != null) {
            envioExistente.setIdDistrito(envioDto.getIdDistrito());
        }
        if (envioDto.getFechSalida() != null) {
            envioExistente.setFechSalida(Date.valueOf(envioDto.getFechSalida()));
        }
        envioExistente
                .setFechLlegada(envioDto.getFechLlegada() != null ? Date.valueOf(envioDto.getFechLlegada()) : null);
        envioExistente.setObservacion(envioDto.getObservacion());
        Envio envioActualizado = envioRepository.save(envioExistente);
        return mapToEnvioListadoDto(envioActualizado);
    }

    @Transactional
    public void eliminarEnvio(Integer idEnvio) {
        Envio envioAEliminar = envioRepository.findById(idEnvio)
                .orElseThrow(() -> new RuntimeException("Envío con ID " + idEnvio + " no encontrado para eliminar."));
        List<DetalleEnvio> detalles = detalleEnvioRepository.findByEnvioIdEnvio(idEnvio);
        for (DetalleEnvio detalle : detalles) {
            if (detalle.getProducto() != null) {
                productoRepository.actualizarEstadoProducto(detalle.getProducto().getIdProducto(),
                        ESTADO_EN_ALMACEN_ID);
            }
        }
        detalleEnvioRepository.deleteByEnvioIdEnvio(idEnvio);
        envioRepository.deleteById(idEnvio);
    }

    public EnvioListadoDto obtenerEnvioPorId(Integer idEnvio) {
        return obtenerTodosLosEnvios().stream()
                .filter(envio -> envio.getIdEnvio().equals(idEnvio))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Envío con ID " + idEnvio + " no encontrado."));
    }
}