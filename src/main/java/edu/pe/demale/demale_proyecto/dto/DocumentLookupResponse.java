package edu.pe.demale.demale_proyecto.dto; // ¡Ajusta este paquete si es diferente!

import lombok.Data;

@Data
public class DocumentLookupResponse {
    private String type;
    private String number;
    private String fullName;
    private String otherInfo;
    private boolean success;
    private String errorMessage;
}