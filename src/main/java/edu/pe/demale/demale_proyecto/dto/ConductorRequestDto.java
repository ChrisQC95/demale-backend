package edu.pe.demale.demale_proyecto.dto;

// DTO for incoming conductor registration/update requests
public class ConductorRequestDto {
    // Persona data
    public String nombres;
    public String apellidos;
    public String razonSocial;
    public String numeroDocumento;
    public String telefono;
    public String correo;
    public Integer idDistrito;
    public Integer idTipoVia;
    public String direccion;
    public String nMunicipal;
    public Integer idTipoDoc;

    // Conductor specific data
    public String licencia;
    public Integer idRol;
}