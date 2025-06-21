package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.TipoCliente; // Ajusta este import a tu paquete 'models'
import edu.pe.demale.demale_proyecto.repositories.TipoClienteRepository; // Ajusta este import a tu paquete 'repositories'
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