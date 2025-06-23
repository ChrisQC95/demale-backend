package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.ProductoRegistroRequest;
import edu.pe.demale.demale_proyecto.dto.ProductoResponse;
import edu.pe.demale.demale_proyecto.models.Productos;
import edu.pe.demale.demale_proyecto.service.ProductoService;

import jakarta.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductosController {

    private final ProductoService productoService;
    private final ObjectMapper objectMapper;

    public ProductosController(ProductoService productoService, ObjectMapper objectMapper) {
        this.productoService = productoService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/registrar")
    public ResponseEntity<ProductoResponse> registrarProducto(@Valid @RequestBody ProductoRegistroRequest request) {
        try {
            ProductoResponse response = productoService.registrarProducto(request, null);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
        } catch (RuntimeException e) {
            System.err.println("Error al registrar producto (sin guía): " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/registrar-con-guia", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductoResponse> registrarProductoConGuia(
            @RequestParam("productoData") String productoJson,
            @RequestParam(value = "guiaRemisionFile", required = false) MultipartFile guiaRemisionFile) {
        try {
            ProductoRegistroRequest request = objectMapper.readValue(productoJson, ProductoRegistroRequest.class);
            ProductoResponse productoRegistrado = productoService.registrarProducto(request, guiaRemisionFile);
            return new ResponseEntity<>(productoRegistrado, HttpStatus.CREATED); // 201 Created
        } catch (RuntimeException e) {
            System.err.println("Error al registrar producto (con guía): " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @GetMapping() // Listar todos los productos
    public ResponseEntity<List<ProductoResponse>> listar() {
        List<Productos> productos = this.productoService.listarProductos();
        List<ProductoResponse> responses = productos.stream()
                .map(productoService::mapProductoToProductoResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK); // 200 OK
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductoResponse> getProductoXID(@PathVariable("id") int id) { // CORREGIDO:
                                                                                         // @PathVariable("id")
        Optional<Productos> productoOptional = this.productoService.obtenerProductoXID(id);
        if (productoOptional.isPresent()) {
            ProductoResponse response = productoService.mapProductoToProductoResponse(productoOptional.get());
            return new ResponseEntity<>(response, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductoResponse> actualizaProductos(@RequestBody Productos productos,
            @PathVariable("id") int id) { // CORREGIDO: @PathVariable("id")
        try {
            // Tu servicio actualizaProductos devuelve la entidad 'Productos'
            Productos productoActualizado = this.productoService.actualizaProductos(productos, id);
            ProductoResponse response = productoService.mapProductoToProductoResponse(productoActualizado);
            return new ResponseEntity<>(response, HttpStatus.OK); // 200 OK
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            // Si la RuntimeException es por "Producto no encontrado", sería 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // O HttpStatus.BAD_REQUEST si el error es de datos
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @DeleteMapping(path = "/{id}") // Eliminar producto
    public ResponseEntity<String> eliminarProducto(@PathVariable("id") int id) { // CORREGIDO: @PathVariable("id")
        boolean ok = this.productoService.eliminarProducto(id);
        if (ok) {
            return new ResponseEntity<>("Producto con id " + id + " eliminado correctamente", HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>("No se pudo eliminar el producto con id " + id, HttpStatus.NOT_FOUND); // 404
                                                                                                               // Not
                                                                                                               // Found
        }
    }

    @GetMapping("/por-cliente/{idCliente}") // Obtener productos por cliente
    public ResponseEntity<List<ProductoResponse>> getProductosByCliente(@PathVariable Integer idCliente) {
        try {
            List<ProductoResponse> productos = productoService.obtenerProductosPorCliente(idCliente);
            if (productos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content si no hay productos
            }
            return new ResponseEntity<>(productos, HttpStatus.OK); // 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}