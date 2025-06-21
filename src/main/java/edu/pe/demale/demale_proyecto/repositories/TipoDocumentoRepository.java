package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
    // Spring Data JPA generará automáticamente los métodos CRUD básicos para
    // TipoDocumento
    // Puedes añadir métodos personalizados aquí si necesitas consultas específicas,
    // por ejemplo:
    // Optional<TipoDocumento> findByNombreDoc(String nombreDoc);
}