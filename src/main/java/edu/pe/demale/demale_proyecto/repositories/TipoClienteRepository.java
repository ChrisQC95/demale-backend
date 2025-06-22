package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoClienteRepository extends JpaRepository<TipoCliente, Integer> {
}