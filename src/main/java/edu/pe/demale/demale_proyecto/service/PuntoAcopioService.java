package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.PuntoAcopio;
import edu.pe.demale.demale_proyecto.repositories.PuntoAcopioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuntoAcopioService {

    private final PuntoAcopioRepository puntoAcopioRepository;

    @Autowired
    public PuntoAcopioService(PuntoAcopioRepository puntoAcopioRepository) {
        this.puntoAcopioRepository = puntoAcopioRepository;
    }

    public List<PuntoAcopio> findAllPuntosAcopio() {
        return puntoAcopioRepository.findAll();
    }
}