package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.TrabajadorDropdownDto; // Tu DTO para el dropdown
import edu.pe.demale.demale_proyecto.service.TrabajadorService; // Asegúrate de que el paquete del servicio sea 'services'

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trabajadores")
@CrossOrigin(origins = "http://localhost:4200")
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    @Autowired
    public TrabajadorController(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    @GetMapping("/atencion-cliente")
    public ResponseEntity<List<TrabajadorDropdownDto>> obtenerTrabajadoresAtencionCliente() {
        Integer idRolAtencionCliente = 4;
        List<TrabajadorDropdownDto> trabajadores = trabajadorService.obtenerTrabajadoresPorRol(idRolAtencionCliente);
        return new ResponseEntity<>(trabajadores, HttpStatus.OK);
    }
}