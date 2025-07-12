package edu.pe.demale.demale_proyecto.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistorialPuntoDescansoDto {
    private Integer idHistorialDescanso;
    private Integer idEnvio; // Para referencia
    private Integer idPuntoDescanso;
    private String nombrePuntoDescanso; // Nombre del punto de descanso
    private LocalDateTime fechaHoraRegistro;
}