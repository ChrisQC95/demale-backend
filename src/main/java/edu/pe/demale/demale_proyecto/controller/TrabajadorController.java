package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.TrabajadorDropdownDto; // Tu DTO para el dropdown
import edu.pe.demale.demale_proyecto.dto.TrabajadorRequestDTO;
import edu.pe.demale.demale_proyecto.dto.TrabajadorResponseDTO;
import edu.pe.demale.demale_proyecto.service.TrabajadorService; // Asegúrate de que el paquete del servicio sea 'services'


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    //ENDPOINTS PARA TRABAJADORES DE ATENCION AL CLIENTE
    @GetMapping("/atencion-cliente")
    public ResponseEntity<List<TrabajadorDropdownDto>> obtenerTrabajadoresAtencionCliente() {
        Integer idRolAtencionCliente = 4;
        List<TrabajadorDropdownDto> trabajadores = trabajadorService.obtenerTrabajadoresPorRol(idRolAtencionCliente);
        return new ResponseEntity<>(trabajadores, HttpStatus.OK);
    }

    //CRUD COMPLETO LO QUE HICE
    
    // POST /api/trabajadores
    @PostMapping
    public ResponseEntity<TrabajadorResponseDTO> createTrabajador(@RequestBody TrabajadorRequestDTO requestDTO) {
        try {
            TrabajadorResponseDTO newTrabajador = trabajadorService.createTrabajador(requestDTO);
            return new ResponseEntity<>(newTrabajador, HttpStatus.CREATED); // Retorna 201 Created
        } catch (RuntimeException e) {
            // Captura excepciones lanzadas por el servicio (ej. Rol/TipoDocumento no encontrado, validaciones)
            // En un proyecto real, se usaría un GlobalExceptionHandler para un manejo más elegante de errores.
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Retorna 400 Bad Request
        }
    }

    // GET /api/trabajadores
    @GetMapping
    public ResponseEntity<List<TrabajadorResponseDTO>> getAllTrabajadores() {
        List<TrabajadorResponseDTO> trabajadores = trabajadorService.getAllTrabajadores();
        return new ResponseEntity<>(trabajadores, HttpStatus.OK); // Retorna 200 OK
    }

    // GET /api/trabajadores/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorResponseDTO> getTrabajadorById(@PathVariable Integer id) {
        return trabajadorService.getTrabajadorById(id)
                .map(trabajador -> new ResponseEntity<>(trabajador, HttpStatus.OK)) // Retorna 200 OK si encontrado
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Retorna 404 Not Found si no existe
    }

    // PUT /api/trabajadores/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TrabajadorResponseDTO> updateTrabajador(@PathVariable Integer id, @RequestBody TrabajadorRequestDTO requestDTO) {
        try {
            TrabajadorResponseDTO updatedTrabajador = trabajadorService.updateTrabajador(id, requestDTO);
            return new ResponseEntity<>(updatedTrabajador, HttpStatus.OK); // Retorna 200 OK
        } catch (RuntimeException e) {
            // Captura excepciones (ej. Trabajador no encontrado, Rol/TipoDocumento faltante/inválido)
            if (e.getMessage().contains("no encontrado")) { // Simple chequeo de mensaje para 404
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Retorna 404 Not Found
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Retorna 400 Bad Request
        }
    }

     // DELETE /api/trabajadores/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTrabajador(@PathVariable Integer id) {
        try {
            trabajadorService.deleteTrabajador(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content (eliminado con éxito)
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found si no existe
        }
    }
}