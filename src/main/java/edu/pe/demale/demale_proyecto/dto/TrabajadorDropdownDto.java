package edu.pe.demale.demale_proyecto.dto;

// Si usas Lombok para getters/setters, puedes usar @Data
import lombok.Data;

@Data // Anotación de Lombok para generar getters, setters, toString, equals, hashCode
public class TrabajadorDropdownDto {
    private Integer idTrabajador;
    private String nombreCompleto;
}