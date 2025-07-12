package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.dto.PuntoDescansoDropdown;
import edu.pe.demale.demale_proyecto.models.PuntoDescanso;
import edu.pe.demale.demale_proyecto.repositories.PuntoDescansoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PuntoDescansoService {

    private final PuntoDescansoRepository puntoDescansoRepository;

    @Autowired
    public PuntoDescansoService(PuntoDescansoRepository puntoDescansoRepository) {
        this.puntoDescansoRepository = puntoDescansoRepository;
    }

    /**
     * Obtiene todos los puntos de descanso para un dropdown.
     * 
     * @return Lista de PuntoDescansoDropdownDto.
     */
    public List<PuntoDescansoDropdown> obtenerPuntosDescansoParaDropdown() {
        return puntoDescansoRepository.findAll().stream()
                .map(this::mapToPuntoDescansoDropdown)
                .collect(Collectors.toList());
    }

    // --- Métodos de Mapeo Internos ---
    private PuntoDescansoDropdown mapToPuntoDescansoDropdown(PuntoDescanso puntoDescanso) {
        PuntoDescansoDropdown dto = new PuntoDescansoDropdown();
        dto.setIdPuntoDescanso(puntoDescanso.getIdPuntoDescanso());
        dto.setPuntoDescanso(puntoDescanso.getNombrePuntoDescanso());
        return dto;
    }
}