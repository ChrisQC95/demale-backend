// src/main/java/edu/pe/demale/demale_proyecto/repositories/ConductorRepository.java
package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConductorRepository extends JpaRepository<Conductor, Integer> {
    // Add this method to check for existing licenses
    Optional<Conductor> findByLicencia(String licencia);
}