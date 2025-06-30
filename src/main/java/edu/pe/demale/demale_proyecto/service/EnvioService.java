package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.EnvioCreacionDto;
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

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final DetalleEnvioRepository detalleEnvioRepository;
    private final IProductosRepository productoRepository;

    // ID del estado "En Tránsito"
    private static final int ESTADO_EN_TRANSITO_ID = 2;

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
}