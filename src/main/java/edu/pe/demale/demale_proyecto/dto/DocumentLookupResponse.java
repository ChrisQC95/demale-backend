package edu.pe.demale.demale_proyecto.dto; // ¡Ajusta este paquete si es diferente!

import lombok.Data;

@Data
public class DocumentLookupResponse {
    private String type; // "DNI", "RUC", "CEE"
    private String number; // El número de documento consultado
    private String fullName; // Nombre completo (para DNI/CEE) o Razón Social (para RUC)
    private String otherInfo; // Información adicional (ej: apellidos separados, dirección, estado RUC, etc.)
    private boolean success; // true si la consulta fue exitosa, false si hubo un error
    private String errorMessage; // Mensaje de error si success es false (ej: "No se encontró el DNI")
}