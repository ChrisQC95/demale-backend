package edu.pe.demale.demale_proyecto.controller;

import edu.pe.demale.demale_proyecto.models.Vehiculo;
import edu.pe.demale.demale_proyecto.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "http://localhost:4200") // o especifica tu frontend
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public List<Vehiculo> listarVehiculos() {
        return vehiculoService.listarVehiculos();
    }

    @GetMapping("/{id}")
    public Optional<Vehiculo> obtenerVehiculo(@PathVariable Integer id) {
        return vehiculoService.obtenerVehiculoPorId(id);
    }

    @PostMapping
    public Vehiculo registrarVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.registrarVehiculo(vehiculo);
    }

    @PutMapping("/{id}")
    public Vehiculo actualizarVehiculo(@PathVariable Integer id, @RequestBody Vehiculo vehiculo) {
        vehiculo.setIdVehiculo(id);
        return vehiculoService.actualizarVehiculo(vehiculo);
    }

    @DeleteMapping("/{id}")
    public void eliminarVehiculo(@PathVariable Integer id) {
        vehiculoService.eliminarVehiculo(id);
    }
}
