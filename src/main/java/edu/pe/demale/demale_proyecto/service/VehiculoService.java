// src/main/java/edu/pe/demale/demale_proyecto/service/VehiculoService.java
package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.VehiculoDropdownDto;
import edu.pe.demale.demale_proyecto.exception.DuplicateResourceException; // Importar
import edu.pe.demale.demale_proyecto.exception.ResourceNotFoundException; // Importar
import edu.pe.demale.demale_proyecto.models.Vehiculo;
import edu.pe.demale.demale_proyecto.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar

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

    public Vehiculo obtenerVehiculoPorId(Integer id) { // Cambiado a devolver Vehiculo directamente o lanzar excepción
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con ID: " + id));
    }

    /**
     * Registra un nuevo vehículo.
     * Lanza DuplicateResourceException si la placa ya existe.
     * @param vehiculo El objeto Vehiculo a registrar.
     * @return El Vehiculo registrado.
     * @throws DuplicateResourceException si la placa ya existe.
     */
    @Transactional
    public Vehiculo registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculoRepository.findByPlaca(vehiculo.getPlaca()).isPresent()) {
            throw new DuplicateResourceException("Ya existe un vehículo con la placa: " + vehiculo.getPlaca());
        }
        return vehiculoRepository.save(vehiculo);
    }

    /**
     * Actualiza un vehículo existente.
     * Lanza ResourceNotFoundException si el vehículo no existe.
     * Lanza DuplicateResourceException si la nueva placa ya existe en otro vehículo.
     * @param vehiculo El objeto Vehiculo con los datos actualizados.
     * @return El Vehiculo actualizado.
     * @throws ResourceNotFoundException si el vehículo no existe.
     * @throws DuplicateResourceException si la nueva placa ya existe en otro vehículo.
     */
    @Transactional
    public Vehiculo actualizarVehiculo(Vehiculo vehiculo) {
        Vehiculo vehiculoExistente = vehiculoRepository.findById(vehiculo.getIdVehiculo())
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con ID: " + vehiculo.getIdVehiculo()));

        // Verificar si la placa se está cambiando a una que ya existe en OTRO vehículo
        Optional<Vehiculo> vehiculoConMismaPlaca = vehiculoRepository.findByPlaca(vehiculo.getPlaca());
        if (vehiculoConMismaPlaca.isPresent() && !vehiculoConMismaPlaca.get().getIdVehiculo().equals(vehiculo.getIdVehiculo())) {
            throw new DuplicateResourceException("La placa '" + vehiculo.getPlaca() + "' ya está registrada para otro vehículo.");
        }

        // Actualizar los campos del vehículo existente
        vehiculoExistente.setPlaca(vehiculo.getPlaca());
        vehiculoExistente.setMarca(vehiculo.getMarca());
        vehiculoExistente.setModelo(vehiculo.getModelo());
        vehiculoExistente.setCapacidad(vehiculo.getCapacidad());

        return vehiculoRepository.save(vehiculoExistente);
    }

    /**
     * Elimina un vehículo por su ID.
     * Lanza ResourceNotFoundException si el vehículo no existe.
     * @param id El ID del vehículo a eliminar.
     * @throws ResourceNotFoundException si el vehículo no existe.
     */
    @Transactional
    public void eliminarVehiculo(Integer id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehículo no encontrado con ID: " + id);
        }
        vehiculoRepository.deleteById(id);
    }

    public boolean existePlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa).isPresent(); // Usar findByPlaca
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
        dto.setDescripcion(vehiculo.getPlaca() + " - " + vehiculo.getMarca() + " " + vehiculo.getModelo());
        return dto;
    }
}