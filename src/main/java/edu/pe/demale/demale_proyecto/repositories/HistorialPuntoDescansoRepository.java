package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.HistorialPuntoDescanso;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialPuntoDescansoRepository extends JpaRepository<HistorialPuntoDescanso, Integer> {

    @Query("SELECT h FROM HistorialPuntoDescanso h JOIN FETCH h.puntoDescanso pd WHERE h.envio.idEnvio = :idEnvio ORDER BY h.fechaHoraRegistro ASC")
    List<HistorialPuntoDescanso> findByEnvioIdEnvioWithPuntoDescanso(@Param("idEnvio") Integer idEnvio);

    @Modifying
    @Transactional
    void deleteByEnvioIdEnvio(@Param("idEnvio") Integer idEnvio);
}