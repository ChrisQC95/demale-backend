package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.ConductorDropdownDto;
import edu.pe.demale.demale_proyecto.service.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/conductores") // La URL base para este controlador
@CrossOrigin(origins = "http://localhost:4200") // Permite acceso desde tu frontend Angular
public class ConductorController {

    private final ConductorService conductorService;

    @Autowired // Inyección de dependencia por constructor
    public ConductorController(ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    @GetMapping("/dropdown") // Endpoint específico para obtener la lista para el dropdown
    public ResponseEntity<List<ConductorDropdownDto>> getConductoresForDropdown() {
        List<ConductorDropdownDto> conductores = conductorService.obtenerTodosLosConductoresParaDropdown();
        return new ResponseEntity<>(conductores, HttpStatus.OK);
    }
}