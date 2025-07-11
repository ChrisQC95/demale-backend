package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {
    List<Trabajador> findByRol_IdRol(Integer idRol);
}

