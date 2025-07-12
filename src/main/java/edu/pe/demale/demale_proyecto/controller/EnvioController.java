package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.EnvioCreacionDto;
import edu.pe.demale.demale_proyecto.dto.EnvioListadoDto;
import edu.pe.demale.demale_proyecto.models.Envio;
import edu.pe.demale.demale_proyecto.service.EnvioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.pe.demale.demale_proyecto.dto.EnvioUpdateDto;

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
            System.err.println("Error al crear el envío: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping // Maneja las solicitudes GET a /api/envios
    public ResponseEntity<List<EnvioListadoDto>> listarTodosLosEnvios() {
        try {
            List<EnvioListadoDto> envios = envioService.obtenerTodosLosEnvios();
            return new ResponseEntity<>(envios, HttpStatus.OK); // 200 OK
        } catch (Exception e) {
            System.err.println("Error al listar envíos: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idEnvio}") // Maneja solicitudes GET a /api/envios/{idEnvio}
    public ResponseEntity<EnvioUpdateDto> obtenerEnvioPorId(@PathVariable Integer idEnvio) { // <-- ¡Retorna
                                                                                             // EnvioUpdateDto!
        try {
            EnvioUpdateDto envioDto = envioService.obtenerEnvioPorId(idEnvio);
            return new ResponseEntity<>(envioDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            System.err.println("Error al obtener el envío por ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Error inesperado al obtener el envío por ID: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idEnvio}")
    public ResponseEntity<EnvioListadoDto> actualizarEnvio(@PathVariable Integer idEnvio,
            @RequestBody EnvioUpdateDto envioDto) {
        if (envioDto.getIdEnvio() == null || !envioDto.getIdEnvio().equals(idEnvio)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // ID en URL y DTO no coinciden o falta ID
        }
        if (envioDto.getIdConductor() == null ||
                envioDto.getIdVehiculo() == null ||
                envioDto.getIdRuta() == null ||
                envioDto.getIdEstadoEnvio() == null ||
                envioDto.getIdPuntoAcopio() == null ||
                envioDto.getIdDistrito() == null ||
                envioDto.getFechSalida() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            EnvioListadoDto envioActualizadoDto = envioService.actualizarEnvio(envioDto); // <-- El servicio ahora
                                                                                          // devuelve EnvioListadoDto
            return new ResponseEntity<>(envioActualizadoDto, HttpStatus.OK); // 200 OK
        } catch (RuntimeException e) { // Captura la excepción si el envío no se encuentra
            System.err.println("Error al actualizar el envío: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception e) { // Captura cualquier otra excepción
            System.err.println("Error inesperado al actualizar el envío: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @DeleteMapping("/{idEnvio}") // Maneja solicitudes DELETE a /api/envios/{idEnvio}
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Integer idEnvio) {
        try {
            envioService.eliminarEnvio(idEnvio);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content (éxito sin contenido de respuesta)
        } catch (RuntimeException e) { // Captura la excepción si el envío no se encuentra
            System.err.println("Error al eliminar el envío: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception e) { // Captura cualquier otra excepción
            System.err.println("Error inesperado al eliminar el envío: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}