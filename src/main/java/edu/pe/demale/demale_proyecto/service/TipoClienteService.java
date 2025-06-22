package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.TipoCliente;
import edu.pe.demale.demale_proyecto.repositories.TipoClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoClienteService {

    private final TipoClienteRepository tipoClienteRepository;

    public TipoClienteService(TipoClienteRepository tipoClienteRepository) {
        this.tipoClienteRepository = tipoClienteRepository;
    }

    public List<TipoCliente> findAll() {
        return tipoClienteRepository.findAll();
    }
}