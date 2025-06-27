package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.EstadoEnvio;
import edu.pe.demale.demale_proyecto.repositories.EstadoEnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoProductoServiceImple implements EstadoProductoService {

    private final EstadoEnvioRepository estadoEnvioRepository;

    @Autowired
    public EstadoProductoServiceImple(EstadoEnvioRepository estadoEnvioRepository) {
        this.estadoEnvioRepository = estadoEnvioRepository;
    }

    @Override
    public List<EstadoEnvio> findAllEstadosEnvio() {
        return estadoEnvioRepository.findAll();
    }
}