package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.models.TipoDocumento; // Asegúrate de importar tu clase TipoDocumento
import edu.pe.demale.demale_proyecto.service.TipoDocumentoService; // Asegúrate de importar tu clase TipoDocumentoService
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-documento") // La URL que Angular llamará para Tipos de Documento
@CrossOrigin(origins = "http://localhost:4200") // Permite peticiones desde tu aplicación Angular
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService; // Inyección por constructor

    // Inyección de dependencias a través del constructor
    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    @GetMapping // Maneja las peticiones GET a /api/tipos-documento
    public ResponseEntity<List<TipoDocumento>> getAllTiposDocumento() {
        List<TipoDocumento> tiposDocumento = tipoDocumentoService.findAll(); // Llama a tu método findAll() del servicio
        return ResponseEntity.ok(tiposDocumento); // Devuelve la lista con un estado HTTP 200 OK
    }
}