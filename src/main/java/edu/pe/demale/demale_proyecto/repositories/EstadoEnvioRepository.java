package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.EstadoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoEnvioRepository extends JpaRepository<EstadoEnvio, Integer> {
    EstadoEnvio findByEstado(String estado);
}