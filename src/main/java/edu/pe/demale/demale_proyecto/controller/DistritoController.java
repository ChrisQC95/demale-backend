package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.models.Distrito;
import edu.pe.demale.demale_proyecto.service.DistritoService; // Importa tu servicio
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/distritos")
@CrossOrigin(origins = "http://localhost:4200")
public class DistritoController {

    private final DistritoService distritoService; // Inyección por constructor

    public DistritoController(DistritoService distritoService) {
        this.distritoService = distritoService;
    }

    @GetMapping
    public ResponseEntity<List<Distrito>> getAllDistritos() {
        List<Distrito> distritos = distritoService.findAll(); // <-- Llama a tu método findAll()
        return ResponseEntity.ok(distritos);
    }
}