package edu.pe.demale.demale_proyecto.dto;

import lombok.Data; // Asegúrate de tener Lombok en tu pom.xml

@Data // Anotación de Lombok para generar getters, setters, toString, equals, hashCode
public class ConductorDropdownDto {
    private Integer idConductor;
    private String nombreCompleto; // Para mostrar en el dropdown
}