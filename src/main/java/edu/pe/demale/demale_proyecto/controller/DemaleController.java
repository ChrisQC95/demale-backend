package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.models.DemaleModel;
import edu.pe.demale.demale_proyecto.services.DemaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/product-history")
@CrossOrigin(origins = "http://localhost:3000")

public class DemaleController {

    @Autowired
    private DemaleService demaleService;

    /**
     * Endpoint GET para obtener el historial de productos con capacidades de
     * búsqueda y filtrado.
     * Se accede a través de la URL: GET /api/product-history
     * Puede incluir parámetros de consulta opcionales:
     * - search: para búsqueda general por cliente, producto o categoría.
     * - status: para filtrar por el estado del movimiento.
     * - startDate: para filtrar por fecha de ingreso a partir de. Formato ISO 8601
     * (ej. 2025-05-20T10:00:00).
     * - endDate: para filtrar por fecha de ingreso hasta. Formato ISO 8601 (ej.
     * 2025-05-25T23:59:59).
     * 
     * @param search    Término de búsqueda general.
     * @param status    Estado del movimiento.
     * @param startDate Fecha y hora de inicio del rango.
     * @param endDate   Fecha y hora de fin del rango.
     * @return ResponseEntity con una lista de DemaleModel que coinciden con los
     *         criterios,
     *         o una lista vacía si no hay resultados.
     */
    @GetMapping // Mapea las peticiones GET a /api/product-history
    public ResponseEntity<List<DemaleModel>> getProductHistory(
            @RequestParam(required = false) String search, // Parámetro de URL opcional para búsqueda (ej.
                                                           // ?search=Zapatos)
            @RequestParam(required = false) String status, // Parámetro de URL opcional para filtro por estado (ej.
                                                           // ?status=En Proceso)
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, // Parámetro
                                                                                                                         // de
                                                                                                                         // URL
                                                                                                                         // opcional
                                                                                                                         // para
                                                                                                                         // fecha
                                                                                                                         // de
                                                                                                                         // inicio
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) { // Parámetro
                                                                                                                         // de
                                                                                                                         // URL
                                                                                                                         // opcional
                                                                                                                         // para
                                                                                                                         // fecha
                                                                                                                         // de
                                                                                                                         // fin

        // Llama al método del servicio para obtener los movimientos de productos con
        // los filtros
        List<DemaleModel> movements = demaleService.searchAndFilterProductMovements(search, status, startDate, endDate);

        // Retorna la lista de movimientos con un estado HTTP 200 OK.
        // Si la lista está vacía, se enviará un JSON [] al frontend.
        return ResponseEntity.ok(movements);
    }

    /**
     * TODOOOOO PARA PROBARRRRRRRR
     * 
     * @return Un mensaje de confirmación.
     */
    @PostMapping("/seed")
    public ResponseEntity<String> seedData() {
        demaleService.addSampleData();
        return ResponseEntity.ok("Sample data added successfully!");
    }

}