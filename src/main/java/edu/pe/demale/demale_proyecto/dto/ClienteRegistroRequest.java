package edu.pe.demale.demale_proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ClienteRegistroRequest {

    @Size(max = 100, message = "Los nombres no pueden exceder los 100 caracteres")
    private String nombres;

    @Size(max = 100, message = "Los apellidos no pueden exceder los 100 caracteres")
    private String apellidos;

    @Size(max = 200, message = "La razón social no debe exceder los 200 caracteres")
    private String razonSocial;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer idTipoDoc;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20, message = "El número de documento no puede exceder los 20 caracteres")
    private String numeroDocumento;

    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    private String telefono;

    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 100, message = "El correo electrónico no puede exceder los 100 caracteres")
    private String correo;

    private Integer idDistrito;
    private Integer idTipoVia;

    @Size(max = 255, message = "La dirección no puede exceder los 255 caracteres")
    private String direccion;

    @Size(max = 50, message = "El número municipal no puede exceder los 50 caracteres")
    private String nMunicipal;

    @NotNull(message = "El tipo de cliente es obligatorio")
    private Integer idTipoCliente;
}