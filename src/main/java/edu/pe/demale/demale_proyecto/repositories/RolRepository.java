package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.Rol; // Asegúrate de que esta ruta sea correcta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Necesario para métodos de búsqueda personalizados

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    // Si en algún momento necesitas buscar un Rol por su nombre, puedes añadir un método así:
    Optional<Rol> findByNombreRol(String nombreRol); // 'nombreRol' es el nombre de la propiedad en tu entidad Rol
}