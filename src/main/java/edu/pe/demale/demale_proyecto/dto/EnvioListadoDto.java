package edu.pe.demale.demale_proyecto.dto;

import lombok.Data;
import java.sql.Date;

@Data // Anotación de Lombok para generar getters, setters, toString, equals, hashCode
public class EnvioListadoDto {
    private Integer idEnvio;
    private String conductorNombreCompleto; // Nombre del conductor
    private String vehiculoDescripcion; // Marca - Modelo (Placa) del vehículo
    private String rutaDescripcion; // SerialRuta - NombreRuta
    private String estadoEnvioNombre; // Nombre del estado del envío
    private String puntoAcopioNombre; // Nombre del punto de acopio
    private String distritoDestinoNombre; // Nombre del distrito de destino
    private Date fechSalida;
    private Date fechLlegada;
    private String observacion;
}
