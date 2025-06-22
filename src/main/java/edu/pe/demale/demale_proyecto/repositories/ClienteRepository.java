package edu.pe.demale.demale_proyecto.repositories; // Correcto

import edu.pe.demale.demale_proyecto.models.Cliente; // Correcto

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByPersonaNumeroDocumento(String numeroDocumento);

    Optional<Cliente> findByPersonaCorreo(String correo);

    @Query("SELECT c FROM Cliente c JOIN c.persona p WHERE " +
            "LOWER(p.nombres) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.razonSocial) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.numeroDocumento) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Cliente> searchClientes(@Param("query") String query);
}