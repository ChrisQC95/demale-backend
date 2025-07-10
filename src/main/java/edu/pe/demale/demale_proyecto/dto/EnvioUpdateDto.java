package edu.pe.demale.demale_proyecto.dto;

import lombok.Data;
import java.sql.Date; // Para tipos de fecha SQL

@Data // Anotación de Lombok para generar getters, setters, toString, equals, hashCode
public class EnvioUpdateDto {
    private Integer idEnvio; // ID del envío a actualizar (obligatorio)
    private Integer idConductor;
    private Integer idVehiculo;
    private Integer idRuta;
    private Integer idEstadoEnvio; // Podría cambiarse, ej. a "Entregado"
    private Integer idPuntoAcopio;
    private Integer idDistrito;
    private String fechSalida; // String en formato "YYYY-MM-DD"
    private String fechLlegada; // Puede ser null, o una fecha cuando se actualice a "Entregado"
    private String observacion;
}