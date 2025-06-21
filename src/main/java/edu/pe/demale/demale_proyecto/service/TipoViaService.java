package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.TipoVia; // Ajusta este import a tu paquete 'models'
import edu.pe.demale.demale_proyecto.repositories.TipoViaRepository; // Ajusta este import a tu paquete 'repositories'
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoViaService {

    private final TipoViaRepository tipoViaRepository;

    public TipoViaService(TipoViaRepository tipoViaRepository) {
        this.tipoViaRepository = tipoViaRepository;
    }

    /**
     * Obtiene una lista de todos los tipos de vía.
     * 
     * @return Una lista de objetos TipoVia.
     */
    public List<TipoVia> findAll() {
        return tipoViaRepository.findAll();
    }

    // Puedes añadir más métodos específicos para TipoVia si los necesitas
}