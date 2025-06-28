package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.VehiculoDropdownDto;
import edu.pe.demale.demale_proyecto.models.Vehiculo;
import edu.pe.demale.demale_proyecto.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> listarVehiculos() {
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> obtenerVehiculoPorId(Integer id) {
        return vehiculoRepository.findById(id);
    }

    public Vehiculo registrarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo actualizarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public void eliminarVehiculo(Integer id) {
        vehiculoRepository.deleteById(id);
    }

    public boolean existePlaca(String placa) {
        return vehiculoRepository.existsByPlaca(placa);
    }

    public List<VehiculoDropdownDto> obtenerTodosLosVehiculosParaDropdown() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        return vehiculos.stream()
                .map(this::mapToDropdownDto)
                .collect(Collectors.toList());
    }

    private VehiculoDropdownDto mapToDropdownDto(Vehiculo vehiculo) {
        VehiculoDropdownDto dto = new VehiculoDropdownDto();
        dto.setIdVehiculo(vehiculo.getIdVehiculo());
        // Ajusta la descripción a lo que desees mostrar en el dropdown
        dto.setDescripcion(vehiculo.getPlaca() + " - " + vehiculo.getMarca() + " " + vehiculo.getModelo());
        return dto;
    }
}
