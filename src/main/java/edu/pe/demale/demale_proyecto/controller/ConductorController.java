// src/main/java/edu/pe/demale/demale_proyecto/controller/ConductorController.java
package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.ConductorDropdownDto;
import edu.pe.demale.demale_proyecto.dto.ConductorRequestDto;
import edu.pe.demale.demale_proyecto.dto.ConductorResponseDto;
import edu.pe.demale.demale_proyecto.exception.DuplicateResourceException;
import edu.pe.demale.demale_proyecto.exception.ResourceNotFoundException;
import edu.pe.demale.demale_proyecto.models.Distrito;
import edu.pe.demale.demale_proyecto.models.TipoVia;
import edu.pe.demale.demale_proyecto.service.ConductorService;
import edu.pe.demale.demale_proyecto.service.DistritoService;
import edu.pe.demale.demale_proyecto.service.TipoViaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conductores")
// Si tienes una configuración global de CORS (CorsConfig.java), puedes eliminar esta línea:
// @CrossOrigin(origins = "http://localhost:4200")
public class ConductorController {

    private final ConductorService conductorService;
    private final DistritoService distritoService;
    private final TipoViaService tipoViaService;

    @Autowired
    public ConductorController(
        ConductorService conductorService,
        DistritoService distritoService,
        TipoViaService tipoViaService
    ) {
        this.conductorService = conductorService;
        this.distritoService = distritoService;
        this.tipoViaService = tipoViaService;
    }

    /**
     * Registra un nuevo conductor.
     * Maneja conflictos de DNI o licencia duplicados devolviendo HTTP 409.
     * Maneja recursos no encontrados (TipoDocumento, Rol, Distrito, TipoVia) devolviendo HTTP 404.
     * Maneja otros errores internos devolviendo HTTP 500.
     * @param dto El ConductorRequestDto que contiene los detalles del conductor.
     * @return ResponseEntity con mensaje de éxito o error en formato JSON.
     */
    @PostMapping
    public ResponseEntity<?> registrarConductor(@RequestBody ConductorRequestDto dto) {
        try {
            conductorService.registrarConductor(dto);
            return ResponseEntity.ok(Map.of("message", "Conductor registrado correctamente"));
        } catch (DuplicateResourceException e) {
            System.out.println("Error de duplicidad al registrar conductor: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(Map.of("error", e.getMessage()));
        } catch (ResourceNotFoundException e) {
            System.out.println("Error de recurso no encontrado al registrar conductor: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.out.println("Error interno al registrar conductor: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error interno al registrar el conductor. Por favor, intente de nuevo más tarde."));
        }
    }

    /**
     * Actualiza un conductor existente por ID.
     * Maneja conflictos de DNI o licencia duplicados devolviendo HTTP 409.
     * Maneja recursos no encontrados (Conductor, TipoDocumento, Rol, Distrito, TipoVia) devolviendo HTTP 404.
     * Maneja otros errores internos devolviendo HTTP 500.
     * @param id El ID del conductor a actualizar.
     * @param dto El ConductorRequestDto que contiene los detalles actualizados del conductor.
     * @return ResponseEntity con mensaje de éxito o error en formato JSON.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarConductor(@PathVariable Integer id, @RequestBody ConductorRequestDto dto) {
        try {
            conductorService.actualizarConductor(id, dto);
            return ResponseEntity.ok(Map.of("message", "Conductor actualizado correctamente"));
        } catch (DuplicateResourceException e) {
            System.out.println("Error de duplicidad al actualizar conductor: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(Map.of("error", e.getMessage()));
        } catch (ResourceNotFoundException e) {
            System.out.println("Error de recurso no encontrado al actualizar conductor: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.out.println("Error interno al actualizar conductor: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error interno al actualizar el conductor. Por favor, intente de nuevo más tarde."));
        }
    }

    /**
     * Recupera una lista de todos los conductores.
     * @return ResponseEntity que contiene una lista de ConductorResponseDto.
     */
    @GetMapping
    public ResponseEntity<List<ConductorResponseDto>> listarConductores() {
        return ResponseEntity.ok(conductorService.listarConductores());
    }

    /**
     * Recupera una lista de conductores formateada para menús desplegables.
     * @return ResponseEntity que contiene una lista de ConductorDropdownDto.
     */
    @GetMapping("/dropdown")
    public ResponseEntity<List<ConductorDropdownDto>> getConductoresForDropdown() {
        List<ConductorDropdownDto> conductores = conductorService.obtenerTodosLosConductoresParaDropdown();
        return ResponseEntity.ok(conductores);
    }

    /**
     * Recupera una lista de todos los Tipos de Vía.
     * @return ResponseEntity que contiene una lista de TipoVia.
     */
    @GetMapping("/tiposvia/dropdown")
    public ResponseEntity<List<TipoVia>> getTiposViaForDropdown() {
        List<TipoVia> tiposVia = tipoViaService.findAll();
        return ResponseEntity.ok(tiposVia);
    }

    /**
     * Recupera una lista de todos los Distritos.
     * @return ResponseEntity que contiene una lista de Distrito.
     */
    @GetMapping("/distritos/dropdown")
    public ResponseEntity<List<Distrito>> getDistritosForDropdown() {
        List<Distrito> distritos = distritoService.findAll();
        return ResponseEntity.ok(distritos);
    }

    /**
     * Elimina un conductor por su ID.
     * Maneja el caso de recurso no encontrado devolviendo HTTP 404.
     * Maneja otros errores internos devolviendo HTTP 500.
     * @param id El ID del conductor a eliminar.
     * @return ResponseEntity con mensaje de éxito o error en formato JSON.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarConductor(@PathVariable Integer id) {
        try {
            conductorService.eliminarConductor(id);
            return ResponseEntity.ok(Map.of("message", "Conductor eliminado correctamente"));
        } catch (ResourceNotFoundException e) {
            System.out.println("Error de recurso no encontrado al eliminar conductor: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.out.println("Error interno al eliminar conductor: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error interno al eliminar el conductor. Por favor, intente de nuevo más tarde."));
        }
    }
}
