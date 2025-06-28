package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.RutaDropdownDto;
import edu.pe.demale.demale_proyecto.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rutas") // URL base para rutas
@CrossOrigin(origins = "http://localhost:4200") // Permite acceso desde Angular
public class RutaController {

    private final RutaService rutaService;

    @Autowired
    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @GetMapping("/dropdown") // Endpoint para el dropdown
    public ResponseEntity<List<RutaDropdownDto>> getRutasForDropdown() {
        List<RutaDropdownDto> rutas = rutaService.obtenerTodasLasRutasParaDropdown();
        return new ResponseEntity<>(rutas, HttpStatus.OK);
    }
}