package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.models.PuntoAcopio;
import edu.pe.demale.demale_proyecto.service.PuntoAcopioService; // Asegúrate de que el paquete del servicio sea 'services'

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/puntos-acopio")
@CrossOrigin(origins = "http://localhost:4200")
public class PuntoAcopioController {

    private final PuntoAcopioService puntoAcopioService;

    @Autowired
    public PuntoAcopioController(PuntoAcopioService puntoAcopioService) {
        this.puntoAcopioService = puntoAcopioService;
    }

    @GetMapping // Mapea a /api/puntos-acopio
    public ResponseEntity<List<PuntoAcopio>> getAllPuntosAcopio() {
        List<PuntoAcopio> puntosAcopio = puntoAcopioService.findAllPuntosAcopio();
        return new ResponseEntity<>(puntosAcopio, HttpStatus.OK);
    }
}