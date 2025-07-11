package edu.pe.demale.demale_proyecto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate; // Para la fecha de nacimiento

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera constructor sin argumentos
@AllArgsConstructor // Genera constructor con todos los argumentos
public class TrabajadorResponseDTO {
    // Identificador del Trabajador (es el mismo que el Id de la Persona)
    private Integer idTrabajador;

    // Datos de Persona (campos que se muestran del modelo Persona)
    private String nombres;
    private String apellidos;
    private String razonSocial;
    private String numeroDocumento;
    private String telefono;
    private String correo;
    private String direccion;
    private String nMunicipal;

    // Información de las relaciones de Persona (aquí podrías incluir los nombres para facilitar la lectura)
    private Integer idDistrito;
    private String nombreDistrito; // Nombre del distrito para mostrar directamente
    private Integer idTipoVia;
    private String nombreTipoVia; // Nombre del tipo de vía
    private Integer idTipoDocumento;
    private String nombreTipoDocumento; // Nombre del tipo de documento

    // Datos de Trabajador (campos propios del modelo Trabajador)
    private LocalDate fechNacimiento;
    private String foto;

    // Información de Rol (datos del modelo Rol)
    private Integer idRol;
    private String nombreRol; // Nombre del rol para mostrar directamente
}