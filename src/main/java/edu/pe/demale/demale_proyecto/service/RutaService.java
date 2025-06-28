package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.RutaDropdownDto;
import edu.pe.demale.demale_proyecto.models.Ruta;
import edu.pe.demale.demale_proyecto.repositories.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    @Autowired
    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    public List<RutaDropdownDto> obtenerTodasLasRutasParaDropdown() {
        List<Ruta> rutas = rutaRepository.findAll();
        return rutas.stream()
                .map(this::mapToDropdownDto)
                .collect(Collectors.toList());
    }

    private RutaDropdownDto mapToDropdownDto(Ruta ruta) {
        RutaDropdownDto dto = new RutaDropdownDto();
        dto.setIdRuta(ruta.getIdRuta());
        dto.setDescripcionRuta(ruta.getSerialRuta() + " - " + ruta.getNombreRuta());
        return dto;
    }
}