package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.models.TipoVia; // Asegúrate de importar tu clase TipoVia
import edu.pe.demale.demale_proyecto.service.TipoViaService; // Asegúrate de importar tu clase TipoViaService
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-via") // La URL que Angular llamará para Tipos de Vía
@CrossOrigin(origins = "http://localhost:4200") // Permite peticiones desde tu aplicación Angular
public class TipoViaController {

    private final TipoViaService tipoViaService; // Inyección por constructor

    // Inyección de dependencias a través del constructor
    public TipoViaController(TipoViaService tipoViaService) {
        this.tipoViaService = tipoViaService;
    }

    @GetMapping // Maneja las peticiones GET a /api/tipos-via
    public ResponseEntity<List<TipoVia>> getAllTiposVia() {
        List<TipoVia> tiposVia = tipoViaService.findAll(); // Llama a tu método findAll() del servicio
        return ResponseEntity.ok(tiposVia); // Devuelve la lista con un estado HTTP 200 OK
    }
}