package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.RutaDropdownDto;
import edu.pe.demale.demale_proyecto.dto.RutaListadoDto;
import edu.pe.demale.demale_proyecto.dto.RutaCreacionDto;
import edu.pe.demale.demale_proyecto.dto.RutaUpdateDto;
import edu.pe.demale.demale_proyecto.service.RutaService;
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

    @GetMapping
    public ResponseEntity<List<RutaListadoDto>> getAllRutas() {
        List<RutaListadoDto> rutas = rutaService.obtenerTodasLasRutas();
        return new ResponseEntity<>(rutas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutaUpdateDto> getRutaById(@PathVariable Integer id) {
        return rutaService.obtenerRutaPorId(id)
                .map(rutaDto -> new ResponseEntity<>(rutaDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<RutaListadoDto> createRuta(@RequestBody RutaCreacionDto rutaCreacionDto) {
        // Validaciones básicas para campos obligatorios
        if (rutaCreacionDto.getSerialRuta() == null || rutaCreacionDto.getSerialRuta().isEmpty() ||
                rutaCreacionDto.getNombreRuta() == null || rutaCreacionDto.getNombreRuta().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            RutaListadoDto nuevaRuta = rutaService.crearRuta(rutaCreacionDto);
            return new ResponseEntity<>(nuevaRuta, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            // Captura la excepción si el SerialRuta ya existe
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict
        } catch (Exception e) {
            System.err.println("Error al crear ruta: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutaListadoDto> updateRuta(@PathVariable Integer id,
            @RequestBody RutaUpdateDto rutaUpdateDto) {
        // Validaciones básicas para campos obligatorios
        if (rutaUpdateDto.getSerialRuta() == null || rutaUpdateDto.getSerialRuta().isEmpty() ||
                rutaUpdateDto.getNombreRuta() == null || rutaUpdateDto.getNombreRuta().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            RutaListadoDto rutaActualizada = rutaService.actualizarRuta(id, rutaUpdateDto);
            return new ResponseEntity<>(rutaActualizada, HttpStatus.OK);
        } catch (IllegalArgumentException e) { // Captura si el SerialRuta ya existe (más específico)
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict
        } catch (RuntimeException e) { // Captura la excepción si la ruta no se encuentra (más específico que
                                       // Exception)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) { // Captura cualquier otra excepción general
            System.err.println("Error al actualizar ruta: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuta(@PathVariable Integer id) {
        try {
            rutaService.eliminarRuta(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (RuntimeException e) { // Captura la excepción si la ruta no se encuentra
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Error al eliminar ruta: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}