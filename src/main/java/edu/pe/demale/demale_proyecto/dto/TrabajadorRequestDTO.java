package edu.pe.demale.demale_proyecto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate; // Para la fecha de nacimiento

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera constructor sin argumentos
@AllArgsConstructor // Genera constructor con todos los argumentos
public class TrabajadorRequestDTO {
    // Datos de Persona (campos que se encuentran en la tabla 'persona')
    private String nombres;
    private String apellidos;
    private String razonSocial;
    private String numeroDocumento; // Mapea a DocIdentidad en la DB
    private String telefono;
    private String correo;
    private String direccion;
    private String nMunicipal;

    // IDs de las relaciones de Persona (los clientes enviarán el ID de estas entidades relacionadas)
    private Integer idDistrito;
    private Integer idTipoVia;
    private Integer idTipoDocumento; // Mapea a IdTipoDoc en la DB

    // Datos de Trabajador (campos que se encuentran en la tabla 'trabajador')
    private LocalDate fechNacimiento;
    private String foto; // Ruta o URL de la foto

    // ID de la relación de Rol para el trabajador
    private Integer idRol;
}