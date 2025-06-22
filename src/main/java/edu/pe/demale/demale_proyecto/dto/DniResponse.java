package edu.pe.demale.demale_proyecto.dto;

import lombok.Data;

@Data
public class DniResponse {
    private String numeroDni;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto;
    private String codVerifica;
    private Boolean success;
    private String message;
}