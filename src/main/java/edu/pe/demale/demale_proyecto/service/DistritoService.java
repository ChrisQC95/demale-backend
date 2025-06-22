package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.Distrito;
import edu.pe.demale.demale_proyecto.repositories.DistritoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DistritoService {

    private final DistritoRepository distritoRepository;

    public DistritoService(DistritoRepository distritoRepository) {
        this.distritoRepository = distritoRepository;
    }

    public List<Distrito> findAll() {
        return distritoRepository.findAll();
    }

}