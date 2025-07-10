package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.DetalleEnvioId;
import jakarta.transaction.Transactional;
import edu.pe.demale.demale_proyecto.models.DetalleEnvio; // Importa la clave compuesta

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio, DetalleEnvioId> {
    @Modifying // Indica que esta consulta modificará la base de datos (DELETE, UPDATE, INSERT)
    @Transactional
    void deleteByEnvioIdEnvio(Integer idEnvio);

    // busca todos los detalles de envios (productos) asociados a un envio
    List<DetalleEnvio> findByEnvioIdEnvio(Integer idEnvio);
}