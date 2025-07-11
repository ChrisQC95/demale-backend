package edu.pe.demale.demale_proyecto.dto;

// DTO for outgoing conductor responses (e.g., list all conductors)
public class ConductorResponseDto {
    public Integer idPersona;
    public String nombres;
    public String apellidos;
    public String numeroDocumento;
    public String tipoDocumento;
    public String celular; // Corresponds to telefono in Persona model
    public String correo;
    public String licencia;
    public String rol;
    public String razonSocial;     
    public String direccion;       
    public String nMunicipal;  
    public Integer idRol;      // Added for frontend to use in edit mode
    public Integer idTipoDoc;  // Added for frontend to use in edit mode
    public Integer idDistrito; // Added for frontend to use in edit mode
    public Integer idTipoVia;  // Added for frontend to use in edit mode
    public String tipoVia;     // Added for display
}
