package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.models.Rol;
import edu.pe.demale.demale_proyecto.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles") // URL: http://localhost:8080/api/roles
@CrossOrigin(origins = "http://localhost:4200")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> getAllRoles() {
        List<Rol> roles = rolService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}