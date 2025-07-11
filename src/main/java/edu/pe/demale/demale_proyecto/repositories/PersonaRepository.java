package edu.pe.demale.demale_proyecto.repositories; // Confirma que tu paquete es 'repositories' (plural)

import edu.pe.demale.demale_proyecto.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> { // <-- CAMBIO AQUÍ: Long a Integer
    Optional<Persona> findByNumeroDocumento(String numeroDocumento);
}