package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Integer> {
    // JpaRepository ya provee save(), findById(), etc.
}