package edu.pe.demale.demale_proyecto.repositories; // Correcto

import edu.pe.demale.demale_proyecto.models.Distrito; // Correcto
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito, Integer> {
    // Métodos personalizados si son necesarios
}