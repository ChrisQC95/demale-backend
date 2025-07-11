package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.Envio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Integer> {
    @Query("SELECT e FROM Envio e " +
            "LEFT JOIN FETCH e.conductor c " +
            "LEFT JOIN FETCH c.persona p " + // Asumiendo que Conductor tiene una relación 'persona'
            "LEFT JOIN FETCH e.vehiculo v " +
            "LEFT JOIN FETCH e.ruta r " +
            "LEFT JOIN FETCH e.estadoEnvio es " +
            "LEFT JOIN FETCH e.puntoAcopio pa " +
            "LEFT JOIN FETCH e.distrito d")
    List<Envio> findAllWithDetails();
}