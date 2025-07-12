package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.PuntoDescansoDropdown;
import edu.pe.demale.demale_proyecto.service.PuntoDescansoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puntos-descanso") // Endpoint base
@CrossOrigin(origins = "http://localhost:4200")
public class PuntoDescansoController {

    private final PuntoDescansoService puntoDescansoService;

    @Autowired
    public PuntoDescansoController(PuntoDescansoService puntoDescansoService) {
        this.puntoDescansoService = puntoDescansoService;
    }

    @GetMapping("/dropdown")
    public ResponseEntity<List<PuntoDescansoDropdown>> getPuntosDescansoForDropdown() {
        List<PuntoDescansoDropdown> puntos = puntoDescansoService.obtenerPuntosDescansoParaDropdown();
        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }
}