package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.EnvioCreacionDto;
import edu.pe.demale.demale_proyecto.models.Envio;
import edu.pe.demale.demale_proyecto.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/envios") // URL base para los endpoints de envíos
@CrossOrigin(origins = "http://localhost:4200") // Permite acceso desde Angular
public class EnvioController {

    private final EnvioService envioService;

    @Autowired
    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @PostMapping // Maneja las solicitudes POST a /api/envios
    public ResponseEntity<Envio> crearEnvio(@RequestBody EnvioCreacionDto envioDto) {
        // Validación básica de campos requeridos (puedes añadir más con @Valid y
        // @NotNull en el DTO)
        if (envioDto.getIdConductor() == null ||
                envioDto.getIdVehiculo() == null ||
                envioDto.getIdRuta() == null ||
                envioDto.getIdAcopio() == null ||
                envioDto.getIdDestino() == null ||
                envioDto.getFechSalida() == null ||
                envioDto.getIdProductosSeleccionados() == null ||
                envioDto.getIdProductosSeleccionados().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Datos incompletos
        }

        try {
            Envio nuevoEnvio = envioService.crearNuevoEnvio(envioDto);
            return new ResponseEntity<>(nuevoEnvio, HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            // Aquí puedes loggear el error (e.g., usando SLF4J/Logback)
            System.err.println("Error al crear el envío: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}