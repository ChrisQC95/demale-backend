package edu.pe.demale.demale_proyecto.controller;

import org.springframework.web.bind.annotation.RestController;

import edu.pe.demale.demale_proyecto.models.Productos;
import edu.pe.demale.demale_proyecto.service.ProductoService;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("productos")
public class ProductosController {
    @Autowired
    ProductoService productoService;

    @GetMapping()
    public ArrayList<Productos> listar() {
        return this.productoService.listarProductos();
    }

    @PostMapping
    public Productos registrar(@RequestBody Productos productos) {
        return this.productoService.grabarProductos(productos);
    }

    @GetMapping(path = "/{id}")
    public Optional<Productos> getCursoXID(@PathVariable("id") int id) {
        return this.productoService.obtenerProductoXID(id);
    }

    @PutMapping(path = "/{id}")
    public Productos actualizaProductos(@RequestBody Productos productos, @PathVariable("id") int id) {
        return this.productoService.actualizaProductos(productos, id);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarProducto(@PathVariable("id") int id) {
        boolean ok = this.productoService.eliminarProducto(id);
        if (ok) {
            return "Producto con id " + id + " eliminado correctamente";
        } else {
            return "No se pudo eliminar el producto con id " + id;
        }
    }
}
