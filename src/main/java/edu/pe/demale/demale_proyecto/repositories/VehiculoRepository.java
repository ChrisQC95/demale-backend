// src/main/java/edu/pe/demale/demale_proyecto/repositories/VehiculoRepository.java
package edu.pe.demale.demale_proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.pe.demale.demale_proyecto.models.Vehiculo;
import java.util.Optional; 

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
   
    Optional<Vehiculo> findByPlaca(String placa);
}
