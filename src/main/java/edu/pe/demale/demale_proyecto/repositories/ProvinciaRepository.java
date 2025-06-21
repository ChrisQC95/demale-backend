package edu.pe.demale.demale_proyecto.repositories; // <-- Observa la 's' al final de repositories

import edu.pe.demale.demale_proyecto.models.Provincia; // <-- Observa la 's' al final de models
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
    // Métodos personalizados si son necesarios
}