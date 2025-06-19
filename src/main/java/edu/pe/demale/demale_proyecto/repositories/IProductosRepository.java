package edu.pe.demale.demale_proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pe.demale.demale_proyecto.models.Productos;

@Repository
public interface IProductosRepository extends JpaRepository<Productos, Integer> {

}
