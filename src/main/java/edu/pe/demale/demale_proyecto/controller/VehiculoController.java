// src/main/java/edu/pe/demale/demale_proyecto/controller/VehiculoController.java
package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.dto.VehiculoDropdownDto;
import edu.pe.demale.demale_proyecto.exception.DuplicateResourceException; // Importar
import edu.pe.demale.demale_proyecto.exception.ResourceNotFoundException; // Importar
import edu.pe.demale.demale_proyecto.models.Vehiculo;
import edu.pe.demale.demale_proyecto.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importar
import org.springframework.http.ResponseEntity; // Importar
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // Importar Map para respuestas JSON

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "http://localhost:4200")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<List<Vehiculo>> listarVehiculos() {
        return ResponseEntity.ok(vehiculoService.listarVehiculos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVehiculo(@PathVariable Integer id) {
        try {
            Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorId(id);
            return ResponseEntity.ok(vehiculo);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error interno al obtener el vehículo."));
        }
    }

    /**
     * Registra un nuevo vehículo.
     * @param vehiculo El objeto Vehiculo a registrar.
     * @return ResponseEntity con mensaje de éxito o error en formato JSON.
     */
    @PostMapping
    public ResponseEntity<?> registrarVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo nuevoVehiculo = vehiculoService.registrarVehiculo(vehiculo);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Vehículo registrado correctamente", "vehiculo", nuevoVehiculo));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.err.println("Error al registrar vehículo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error interno al registrar el vehículo."));
        }
    }

    /**
     * Actualiza un vehículo existente.
     * @param id El ID del vehículo a actualizar.
     * @param vehiculo El objeto Vehiculo con los datos actualizados.
     * @return ResponseEntity con mensaje de éxito o error en formato JSON.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarVehiculo(@PathVariable Integer id, @RequestBody Vehiculo vehiculo) {
        try {
            vehiculo.setIdVehiculo(id); // Asegurar que el ID del path se use
            Vehiculo vehiculoActualizado = vehiculoService.actualizarVehiculo(vehiculo);
            return ResponseEntity.ok(Map.of("message", "Vehículo actualizado correctamente", "vehiculo", vehiculoActualizado));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", e.getMessage()));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.err.println("Error al actualizar vehículo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error interno al actualizar el vehículo."));
        }
    }

    /**
     * Elimina un vehículo por su ID.
     * @param id El ID del vehículo a eliminar.
     * @return ResponseEntity con mensaje de éxito o error en formato JSON.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarVehiculo(@PathVariable Integer id) {
        try {
            vehiculoService.eliminarVehiculo(id);
            return ResponseEntity.ok(Map.of("message", "Vehículo eliminado correctamente"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error interno al eliminar el vehículo."));
        }
    }

    @GetMapping("/dropdown")
    public List<VehiculoDropdownDto> getVehiculosForDropdown() {
        return vehiculoService.obtenerTodosLosVehiculosParaDropdown();
    }
}
