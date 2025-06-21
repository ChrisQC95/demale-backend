package edu.pe.demale.demale_proyecto.repositories; // Correcto

import edu.pe.demale.demale_proyecto.models.Cliente; // Correcto
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Puedes añadir métodos de consulta personalizados si los necesitas
}