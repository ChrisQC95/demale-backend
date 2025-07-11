// src/main/java/edu/pe/demale/demale_proyecto/repositories/RolRepository.java
package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombreRol(String nombreRol); // optional for assigning by name
}