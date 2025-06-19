package edu.pe.demale.demale_proyecto.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pe.demale.demale_proyecto.models.Productos;
import edu.pe.demale.demale_proyecto.repositories.IProductosRepository;

@Service
public class ProductoService {
    @Autowired
    IProductosRepository productosRepository;

    public ArrayList<Productos> listarProductos() {
        return (ArrayList<Productos>) productosRepository.findAll();
    }

    public Optional<Productos> obtenerProductoXID(int id) {
        return productosRepository.findById(id);
    }

    public Productos grabarProductos(Productos obj) {
        return productosRepository.save(obj);
    }

    public Productos actualizaProductos(Productos obj, int id) {
        Productos p = productosRepository.findById(id).get();
        p.setProducto(obj.getProducto());
        p.setAlto(obj.getAlto());
        p.setAncho(obj.getAncho());
        p.setLargo(obj.getLargo());
        p.setPeso(obj.getPeso());
        p.setFechIngreso(obj.getFechIngreso());
        p.setFechLlegada(obj.getFechLlegada());
        p.setIdPuntoAcopio(obj.getIdPuntoAcopio());
        p.setIdTipoProducto(obj.getIdTipoProducto());
        p.setIdCliente(obj.getIdCliente());
        p.setIdEstadoEnvio(obj.getIdEstadoEnvio());
        p.setIdDistrito(obj.getIdDistrito());
        productosRepository.save(p);
        return p;
    }

    public Boolean eliminarProducto(int id) {
        productosRepository.deleteById(id);
        return true;
    }
}
