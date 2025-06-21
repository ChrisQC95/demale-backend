package edu.pe.demale.demale_proyecto.dto;

import lombok.Data; // Requiere la dependencia Lombok
import java.time.LocalDate;

@Data // Anotación de Lombok para generar getters, setters, toString, equals y
      // hashCode
public class ClienteResponse {

    private Integer idCliente;
    private String nombres;
    private String apellidos;
    private String razonSocial;
    private String tipoDocumentoNombre; // Nombre del tipo de documento (ej. DNI, RUC)
    private String numeroDocumento;
    private String telefono;
    private String correo;
    private String distritoNombre; // Nombre del distrito
    private String tipoViaNombre; // Nombre del tipo de vía
    private String direccionCompleta; // Combinación de tipo de vía, dirección y número municipal
    private String tipoClienteNombre; // Nombre del tipo de cliente (ej. Persona Natural, Empresa)
    private LocalDate fechaRegistro;
    private Boolean estado;
    private Integer idTipoCliente; // Añadir esta línea
    private Integer idDistrito; // Añadir esta línea
    private Integer idTipoVia; // Añadir esta línea
    private String direccion; // Añadir esta línea
    private String nMunicipal;
}