package edu.pe.demale.demale_proyecto.service;

import edu.pe.demale.demale_proyecto.models.TipoProducto;
import edu.pe.demale.demale_proyecto.repositories.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProductoService {

    private final TipoProductoRepository tipoProductoRepository;

    @Autowired
    public TipoProductoService(TipoProductoRepository tipoProductoRepository) {
        this.tipoProductoRepository = tipoProductoRepository;
    }

    public List<TipoProducto> findAllTiposProducto() {
        return tipoProductoRepository.findAll();
    }
}