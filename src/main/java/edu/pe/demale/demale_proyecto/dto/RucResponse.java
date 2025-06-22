package edu.pe.demale.demale_proyecto.dto; // ¡Ajusta este paquete si es diferente!

import lombok.Data;

@Data
public class RucResponse {
    private String numeroRuc;
    private String razonSocial;
    private String nombreComercial;
    private String direccion;
    private String estado;
    private String condicion;
    private Boolean success;
    private String message;
}