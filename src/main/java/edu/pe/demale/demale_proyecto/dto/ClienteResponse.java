package edu.pe.demale.demale_proyecto.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClienteResponse {

    private Integer idCliente;
    private String nombres;
    private String apellidos;
    private String razonSocial;
    private String nombreCompleto;
    private String tipoDocumentoNombre;
    private String numeroDocumento;
    private String telefono;
    private String correo;
    private String distritoNombre;
    private String tipoViaNombre;
    private String direccionCompleta;
    private String tipoClienteNombre;
    private LocalDate fechaRegistro;
    private Boolean estado;
    private Integer idTipoCliente;
    private Integer idDistrito;
    private Integer idTipoVia;
    private String direccion;
    private String nMunicipal;
}
