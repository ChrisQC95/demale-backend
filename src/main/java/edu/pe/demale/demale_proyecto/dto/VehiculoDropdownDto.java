package edu.pe.demale.demale_proyecto.dto;

import lombok.Data; // Asegúrate de tener Lombok en tu pom.xml

@Data // Genera getters, setters, toString, equals y hashCode
public class VehiculoDropdownDto {
    private Integer idVehiculo;
    private String descripcion; // Ej: "ABC-123 - Toyota Corolla"
}