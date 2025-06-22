package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto, Integer> {
}