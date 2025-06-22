package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.TrabajadorDropdownDto;
import edu.pe.demale.demale_proyecto.models.Trabajador;
import edu.pe.demale.demale_proyecto.repositories.TrabajadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;

    @Autowired
    public TrabajadorService(TrabajadorRepository trabajadorRepository) {
        this.trabajadorRepository = trabajadorRepository;
    }

    public List<TrabajadorDropdownDto> obtenerTrabajadoresPorRol(Integer idRol) {
        List<Trabajador> trabajadores = trabajadorRepository.findByRol_IdRol(idRol);
        return trabajadores.stream()
                .map(this::mapToDropdownDto)
                .collect(Collectors.toList());
    }

    private TrabajadorDropdownDto mapToDropdownDto(Trabajador trabajador) {
        TrabajadorDropdownDto dto = new TrabajadorDropdownDto();
        dto.setIdTrabajador(trabajador.getIdTrabajador());
        if (trabajador.getPersona() != null) {
            dto.setNombreCompleto(trabajador.getPersona().getNombres() + " " + trabajador.getPersona().getApellidos());
        } else {
            dto.setNombreCompleto("Nombre Desconocido");
        }
        return dto;
    }
}