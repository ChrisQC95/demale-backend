package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.PuntoDescanso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Para buscar por nombre único

@Repository
public interface PuntoDescansoRepository extends JpaRepository<PuntoDescanso, Integer> {
    // Método para buscar por el nombre del punto de descanso (para validación de
    // unicidad)
    Optional<PuntoDescanso> findByNombrePuntoDescanso(String nombrePuntoDescanso);
}
