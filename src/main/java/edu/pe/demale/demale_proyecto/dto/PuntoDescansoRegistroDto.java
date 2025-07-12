package edu.pe.demale.demale_proyecto.dto;

import lombok.Data; // Asegúrate de tener Lombok en tu pom.xml

@Data
public class PuntoDescansoRegistroDto {
    private Integer idPuntoDescanso;
    private boolean llegadaFinal; // Indica si es la llegada final
}
