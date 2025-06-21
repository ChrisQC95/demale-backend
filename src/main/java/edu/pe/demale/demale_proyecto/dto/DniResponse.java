package edu.pe.demale.demale_proyecto.dto; // ¡Ajusta este paquete si es diferente!

import lombok.Data; // Requiere la dependencia de Lombok en tu pom.xml

@Data // Anotación de Lombok para generar getters, setters, toString, etc.
public class DniResponse {
    private String numeroDni;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto; // Si la API lo proporciona unificado
    private String codVerifica;
    private Boolean success; // api.apis.net.pe a veces incluye esto
    private String message; // Para mensajes de error de la API externa
}