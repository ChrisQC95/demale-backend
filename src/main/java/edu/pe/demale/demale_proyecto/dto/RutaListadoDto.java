package edu.pe.demale.demale_proyecto.dto;

import lombok.Data; // Asegúrate de tener Lombok en tu pom.xml

@Data // Anotación de Lombok para generar getters, setters, toString, equals, hashCode
public class RutaListadoDto {
    private Integer idRuta;
    private String serialRuta;
    private String nombreRuta; // Corresponde al campo 'Ruta' en la base de datos
    private String glosa;
}
