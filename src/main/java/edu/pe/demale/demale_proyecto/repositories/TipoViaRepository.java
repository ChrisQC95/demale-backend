package edu.pe.demale.demale_proyecto.repositories; // Correcto

import edu.pe.demale.demale_proyecto.models.TipoVia; // Correcto
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoViaRepository extends JpaRepository<TipoVia, Integer> {
}