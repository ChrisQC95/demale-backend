package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.models.EstadoEnvio;
import edu.pe.demale.demale_proyecto.service.EstadoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados-envio")
@CrossOrigin(origins = "http://localhost:4200")
public class EstadoProductoController {

    private final EstadoProductoService estadoProductoService;

    @Autowired
    public EstadoProductoController(EstadoProductoService estadoProductoService) {
        this.estadoProductoService = estadoProductoService;
    }

    @GetMapping
    public ResponseEntity<List<EstadoEnvio>> getAllEstadosEnvio() {
        List<EstadoEnvio> estados = estadoProductoService.findAllEstadosEnvio();
        return new ResponseEntity<>(estados, HttpStatus.OK);
    }
}