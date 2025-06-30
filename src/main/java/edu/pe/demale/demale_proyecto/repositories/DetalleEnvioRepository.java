package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.DetalleEnvioId;
import edu.pe.demale.demale_proyecto.models.DetalleEnvio; // Importa la clave compuesta
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio, DetalleEnvioId> {
    // Para insertar con JpaRepository puedes usar .save(new DetalleEnvio(idEnvio,
    // idProducto))
    // Si prefieres una consulta nativa explícita (menos común con JPA pero
    // posible):
    // @Modifying
    // @Query(value = "INSERT INTO detalleenvios (IdEnvio, IdProducto) VALUES
    // (:idEnvio, :idProducto)", nativeQuery = true)
    // void insertarDetalleEnvio(@Param("idEnvio") Integer idEnvio,
    // @Param("idProducto") Integer idProducto);
}