package edu.pe.demale.demale_proyecto.dto; // ¡Ajusta este paquete si es diferente!

import lombok.Data;

@Data
public class CeeResponse {
    private String numeroCee; // O el nombre de campo exacto que use factiliza.com para el número de carnet
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto; // Si factiliza lo unifica
    private Boolean success; // Si factiliza.com lo incluye
    private String message;
    // ¡Importante! Si la API de Factiliza devuelve más campos relevantes, agrégalos
    // aquí.
    // Revisa la documentación de Factiliza para Carnet de Extranjería.
}