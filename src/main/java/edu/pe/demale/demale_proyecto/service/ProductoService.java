package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.ProductoRegistroRequest;
import edu.pe.demale.demale_proyecto.dto.ProductoResponse;
import edu.pe.demale.demale_proyecto.models.*;
import edu.pe.demale.demale_proyecto.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final IProductosRepository productosRepository;
    private final PuntoAcopioRepository puntoAcopioRepository;
    private final TipoProductoRepository tipoProductoRepository;
    private final ClienteRepository clienteRepository;
    private final EstadoEnvioRepository estadoEnvioRepository;
    private final DistritoRepository distritoRepository;
    private final TrabajadorRepository trabajadorRepository;

    @Autowired
    public ProductoService(IProductosRepository productosRepository,
            PuntoAcopioRepository puntoAcopioRepository,
            TipoProductoRepository tipoProductoRepository,
            ClienteRepository clienteRepository,
            EstadoEnvioRepository estadoEnvioRepository,
            DistritoRepository distritoRepository,
            TrabajadorRepository trabajadorRepository) {
        this.productosRepository = productosRepository;
        this.puntoAcopioRepository = puntoAcopioRepository;
        this.tipoProductoRepository = tipoProductoRepository;
        this.clienteRepository = clienteRepository;
        this.estadoEnvioRepository = estadoEnvioRepository;
        this.distritoRepository = distritoRepository;
        this.trabajadorRepository = trabajadorRepository;
    }

    public ArrayList<Productos> listarProductos() {
        return (ArrayList<Productos>) productosRepository.findAll();
    }

    public Optional<Productos> obtenerProductoXID(int id) {
        return productosRepository.findById(id);
    }

    public Productos grabarProductos(Productos obj) {
        return productosRepository.save(obj);
    }

    @Transactional
    public Productos actualizaProductos(Productos obj, int id) {
        Productos p = productosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        p.setProducto(obj.getProducto());
        p.setAlto(obj.getAlto());
        p.setAncho(obj.getAncho());
        p.setLargo(obj.getLargo());
        p.setPeso(obj.getPeso());
        p.setFechIngreso(obj.getFechIngreso());
        p.setFechLlegada(obj.getFechLlegada());
        p.setPuntoAcopio(obj.getPuntoAcopio());
        p.setTipoProducto(obj.getTipoProducto());
        p.setCliente(obj.getCliente());
        p.setEstadoEnvio(obj.getEstadoEnvio());
        p.setDistrito(obj.getDistrito());
        p.setGuiaRemision(obj.getGuiaRemision());
        if (obj.getTrabajador() != null) {
            p.setTrabajador(obj.getTrabajador());
        }

        productosRepository.save(p);
        return p;
    }

    public Boolean eliminarProducto(int id) {
        if (productosRepository.existsById(id)) {
            productosRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public ProductoResponse registrarProducto(ProductoRegistroRequest request, MultipartFile guiaRemisionFile)
            throws IOException {
        PuntoAcopio puntoAcopio = puntoAcopioRepository.findById(request.getIdPuntoAcopio())
                .orElseThrow(() -> new RuntimeException(
                        "Punto de acopio no encontrado con ID: " + request.getIdPuntoAcopio()));

        TipoProducto tipoProducto = tipoProductoRepository.findById(request.getIdTipoProducto())
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de producto no encontrado con ID: " + request.getIdTipoProducto()));

        Cliente cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + request.getIdCliente()));
        EstadoEnvio estadoEnAlmacen = estadoEnvioRepository.findByEstado("En Almacén");
        if (estadoEnAlmacen == null) {
            throw new RuntimeException(
                    "Estado de envío 'En Almacén' no encontrado en la base de datos. Por favor, asegúrate de que exista.");
        }

        Distrito distritoDestino = distritoRepository.findById(request.getIdDistrito())
                .orElseThrow(() -> new RuntimeException(
                        "Distrito de destino no encontrado con ID: " + request.getIdDistrito()));
        Trabajador trabajador = trabajadorRepository.findById(request.getIdTrabajador())
                .orElseThrow(() -> new RuntimeException(
                        "Trabajador (Empleado) no encontrado con ID: " + request.getIdTrabajador()));

        Productos producto = new Productos();
        producto.setProducto(request.getProducto());
        producto.setAlto(request.getAlto());
        producto.setAncho(request.getAncho());
        producto.setLargo(request.getLargo());
        producto.setPeso(request.getPeso());
        producto.setFechIngreso(LocalDate.now());
        producto.setFechLlegada(null);
        producto.setPuntoAcopio(puntoAcopio);
        producto.setTipoProducto(tipoProducto);
        producto.setCliente(cliente);
        producto.setEstadoEnvio(estadoEnAlmacen);
        producto.setDistrito(distritoDestino);
        producto.setTrabajador(trabajador);

        if (guiaRemisionFile != null && !guiaRemisionFile.isEmpty()) {
            producto.setGuiaRemision(guiaRemisionFile.getBytes());
        } else {
            producto.setGuiaRemision(null);
        }

        Productos productoGuardado = productosRepository.save(producto);

        return mapProductoToProductoResponse(productoGuardado);
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> obtenerProductosPorCliente(Integer idCliente) {
        List<Productos> productos = productosRepository.findByClienteIdCliente(idCliente);
        return productos.stream()
                .map(this::mapProductoToProductoResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public byte[] downloadGuiaRemision(Integer idProducto) {
        Productos producto = productosRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado para descargar guía de remisión con ID: " + idProducto));
        return producto.getGuiaRemision();
    }

    private ProductoResponse mapProductoToProductoResponse(Productos producto) {
        ProductoResponse response = new ProductoResponse();
        response.setIdProducto(producto.getIdProducto());
        response.setProducto(producto.getProducto());
        response.setAlto(producto.getAlto());
        response.setAncho(producto.getAncho());
        response.setLargo(producto.getLargo());
        response.setPeso(producto.getPeso());
        response.setFechIngreso(producto.getFechIngreso());
        response.setFechLlegada(producto.getFechLlegada());

        if (producto.getPuntoAcopio() != null) {
            response.setPuntoAcopioNombre(producto.getPuntoAcopio().getNombreAcopio());
            response.setIdPuntoAcopio(producto.getPuntoAcopio().getIdPuntoAcopio());
        }
        if (producto.getTipoProducto() != null) {
            response.setTipoProductoNombre(producto.getTipoProducto().getTipoProducto());
            response.setIdTipoProducto(producto.getTipoProducto().getIdTipoProducto());
        }
        if (producto.getCliente() != null && producto.getCliente().getPersona() != null) {
            if (producto.getCliente().getPersona().getRazonSocial() != null
                    && !producto.getCliente().getPersona().getRazonSocial().trim().isEmpty()) {
                response.setClienteNombreCompleto(producto.getCliente().getPersona().getRazonSocial());
            } else if (producto.getCliente().getPersona().getNombres() != null
                    && !producto.getCliente().getPersona().getNombres().trim().isEmpty() &&
                    producto.getCliente().getPersona().getApellidos() != null
                    && !producto.getCliente().getPersona().getApellidos().trim().isEmpty()) {
                response.setClienteNombreCompleto(producto.getCliente().getPersona().getNombres() + " "
                        + producto.getCliente().getPersona().getApellidos());
            } else {
                response.setClienteNombreCompleto("Cliente Desconocido");
            }
            response.setIdCliente(producto.getCliente().getIdCliente());
        }
        if (producto.getEstadoEnvio() != null) {
            response.setEstadoEnvioNombre(producto.getEstadoEnvio().getEstado());
            response.setIdEstadoEnvio(producto.getEstadoEnvio().getIdEstadoEnvio());
        }
        if (producto.getDistrito() != null) {
            response.setDistritoDestinoNombre(producto.getDistrito().getNombreDistrito());
            response.setIdDistrito(producto.getDistrito().getIdDistrito());
        }
        if (producto.getTrabajador() != null && producto.getTrabajador().getPersona() != null) {
            String nombreTrabajador = producto.getTrabajador().getPersona().getNombres() + " "
                    + producto.getTrabajador().getPersona().getApellidos();
            response.setTrabajadorNombre(nombreTrabajador.trim());
            response.setIdTrabajador(producto.getTrabajador().getIdTrabajador());
        } else {
            response.setTrabajadorNombre("Trabajador Desconocido");
        }

        if (producto.getGuiaRemision() != null && producto.getGuiaRemision().length > 0) {
            response.setGuiaRemisionBase64(Base64.getEncoder().encodeToString(producto.getGuiaRemision()));
        }

        return response;
    }
}