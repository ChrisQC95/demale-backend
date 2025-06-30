package edu.pe.demale.demale_proyecto.repositories;

import edu.pe.demale.demale_proyecto.models.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductosRepository extends JpaRepository<Productos, Integer> {
    List<Productos> findByClienteIdCliente(Integer idCliente);

    List<Productos> findByPuntoAcopioIdPuntoAcopio(Integer idPuntoAcopio);

    List<Productos> findByEstadoEnvioIdEstadoEnvio(Integer idEstadoEnvio);

    List<Productos> findByPuntoAcopioIdPuntoAcopioAndEstadoEnvioIdEstadoEnvio(Integer idPuntoAcopio,
            Integer idEstadoEnvio);

    List<Productos> findByEstadoEnvio_IdEstadoEnvio(Integer idEstadoEnvio);

    List<Productos> findByClienteIdClienteAndEstadoEnvio_IdEstadoEnvio(Integer idCliente, Integer idEstadoEnvio);

    @Modifying
    @Query(value = "UPDATE producto SET IdEstadoEnvio = :idEstadoEnvio WHERE IdProducto = :idProducto", nativeQuery = true)
    void actualizarEstadoProducto(@Param("idProducto") Integer idProducto,
            @Param("idEstadoEnvio") Integer idEstadoEnvio);

}