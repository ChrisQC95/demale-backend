package edu.pe.demale.demale_proyecto.service; // Ajusta este paquete

import edu.pe.demale.demale_proyecto.models.TipoDocumento; // Ajusta el paquete de tu entidad
import edu.pe.demale.demale_proyecto.repositories.TipoDocumentoRepository; // Ajusta el paquete de tu repositorio
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