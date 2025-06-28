package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.ConductorDropdownDto;
import edu.pe.demale.demale_proyecto.models.Conductor;
import edu.pe.demale.demale_proyecto.models.Persona; // Asegúrate de importar Persona
import edu.pe.demale.demale_proyecto.repositories.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors; // Necesario para .stream() y .collect()

@Service
public class ConductorService {

    private final ConductorRepository conductorRepository;

    @Autowired // Inyección de dependencia por constructor
    public ConductorService(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    // Método para obtener todos los conductores en formato de dropdown
    public List<ConductorDropdownDto> obtenerTodosLosConductoresParaDropdown() {
        List<Conductor> conductores = conductorRepository.findAll();
        return conductores.stream()
                .map(this::mapToDropdownDto)
                .collect(Collectors.toList());
    }

    // Método de mapeo de entidad Conductor a DTO ConductorDropdownDto
    private ConductorDropdownDto mapToDropdownDto(Conductor conductor) {
        ConductorDropdownDto dto = new ConductorDropdownDto();
        dto.setIdConductor(conductor.getIdConductor());

        // Asumiendo que tu entidad Conductor tiene un objeto Persona asociado
        // La entidad Persona debe tener Nombres, Apellidos y RazonSocial.
        if (conductor.getPersona() != null) {
            Persona persona = conductor.getPersona();
            // Prioriza RazonSocial si existe y no está vacía (para personas
            // jurídicas/empresas)
            if (persona.getRazonSocial() != null && !persona.getRazonSocial().trim().isEmpty()) {
                dto.setNombreCompleto(persona.getRazonSocial());
            } else {
                // Si no hay RazonSocial, usa Nombres y Apellidos (para personas naturales)
                String nombres = persona.getNombres() != null ? persona.getNombres() : "";
                String apellidos = persona.getApellidos() != null ? persona.getApellidos() : "";
                dto.setNombreCompleto((nombres + " " + apellidos).trim());
            }
        } else {
            dto.setNombreCompleto("Conductor Desconocido"); // Fallback si no hay Persona asociada
        }
        return dto;
    }
}