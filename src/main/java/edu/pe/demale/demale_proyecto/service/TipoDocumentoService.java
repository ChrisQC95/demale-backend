package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.TipoDocumento;
import edu.pe.demale.demale_proyecto.repositories.TipoDocumentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoDocumentoService {

    private final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoService(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    public List<TipoDocumento> findAll() {
        return tipoDocumentoRepository.findAll();
    }

    // Puedes añadir más métodos si necesitas lógica de negocio específica
}