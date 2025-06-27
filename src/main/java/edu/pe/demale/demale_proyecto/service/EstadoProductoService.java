package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.EstadoEnvio;
import java.util.List;

public interface EstadoProductoService {
    List<EstadoEnvio> findAllEstadosEnvio();
}