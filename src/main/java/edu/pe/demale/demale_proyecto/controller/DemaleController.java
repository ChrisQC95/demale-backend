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

    @GetMapping
    public ResponseEntity<List<DemaleModel>> getProductHistory(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<DemaleModel> movements = demaleService.searchAndFilterProductMovements(search, status, startDate, endDate);
        return ResponseEntity.ok(movements);
    }

    @PostMapping("/seed")
    public ResponseEntity<String> seedData() {
        demaleService.addSampleData();
        return ResponseEntity.ok("Sample data added successfully!");
    }

}