package edu.pe.demale.demale_proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.pe.demale.demale_proyecto.models.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    boolean existsByPlaca(String placa);
}
