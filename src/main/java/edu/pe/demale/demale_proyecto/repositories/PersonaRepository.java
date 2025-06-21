package edu.pe.demale.demale_proyecto.repositories; // Correcto

import edu.pe.demale.demale_proyecto.models.Persona; // Correcto
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    // Puedes añadir métodos de consulta personalizados aquí si los necesitas
    // Por ejemplo, para buscar una persona por su número de documento:
    Optional<Persona> findByNumeroDocumento(String numeroDocumento);
}