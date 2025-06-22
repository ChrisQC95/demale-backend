package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.TipoVia;
import edu.pe.demale.demale_proyecto.repositories.TipoViaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoViaService {

    private final TipoViaRepository tipoViaRepository;

    public TipoViaService(TipoViaRepository tipoViaRepository) {
        this.tipoViaRepository = tipoViaRepository;
    }

    public List<TipoVia> findAll() {
        return tipoViaRepository.findAll();
    }
}