package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.Distrito; // Ajusta este import a tu paquete 'models'
import edu.pe.demale.demale_proyecto.repositories.DistritoRepository; // Ajusta este import a tu paquete 'repositories'
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DistritoService {

    private final DistritoRepository distritoRepository;

    public DistritoService(DistritoRepository distritoRepository) {
        this.distritoRepository = distritoRepository;
    }

    /**
     * Obtiene una lista de todos los distritos.
     * 
     * @return Una lista de objetos Distrito.
     */
    public List<Distrito> findAll() {
        return distritoRepository.findAll();
    }

    // Puedes añadir más métodos específicos para Distrito si los necesitas
}