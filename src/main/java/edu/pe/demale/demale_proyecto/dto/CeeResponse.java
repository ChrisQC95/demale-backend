package edu.pe.demale.demale_proyecto.dto;

import lombok.Data;

@Data
public class CeeResponse {
    private String numeroCee;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto;
    private Boolean success;
    private String message;
}