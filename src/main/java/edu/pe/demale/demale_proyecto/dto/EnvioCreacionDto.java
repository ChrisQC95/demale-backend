package edu.pe.demale.demale_proyecto.dto;

import lombok.Data; // Asegúrate de tener Lombok en tu pom.xml
import java.util.List;

@Data // Anotación de Lombok
public class EnvioCreacionDto {
    private Integer idConductor;
    private Integer idVehiculo;
    private Integer idRuta;
    private String fechSalida; // Formato "YYYY-MM-DD"
    private String observacion;
    private List<Integer> idProductosSeleccionados; // IDs de los productos marcados
}