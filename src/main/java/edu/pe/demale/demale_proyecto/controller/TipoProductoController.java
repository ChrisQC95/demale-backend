package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.models.TipoProducto;
import edu.pe.demale.demale_proyecto.service.TipoProductoService; // Asegúrate de que el paquete del servicio sea 'services'

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-producto")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoProductoController {

    private final TipoProductoService tipoProductoService;

    @Autowired
    public TipoProductoController(TipoProductoService tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    @GetMapping
    public ResponseEntity<List<TipoProducto>> getAllTiposProducto() {
        List<TipoProducto> tiposProducto = tipoProductoService.findAllTiposProducto();
        return new ResponseEntity<>(tiposProducto, HttpStatus.OK);
    }
}