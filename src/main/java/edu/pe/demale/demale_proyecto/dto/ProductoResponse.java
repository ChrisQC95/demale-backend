package edu.pe.demale.demale_proyecto.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;

@Data // Anotación de Lombok
public class ProductoResponse {
    private Integer idProducto;
    private String producto;
    private BigDecimal alto;
    private BigDecimal ancho;
    private BigDecimal largo;
    private BigDecimal peso;
    private LocalDate fechIngreso;
    private LocalDate fechLlegada;

    private String puntoAcopioNombre;
    private String tipoProductoNombre;
    private String clienteNombreCompleto;
    private String estadoEnvioNombre;
    private String distritoDestinoNombre;
    private String trabajadorNombre;

    private Integer idPuntoAcopio;
    private Integer idTipoProducto;
    private Integer idCliente;
    private Integer idEstadoEnvio;
    private Integer idDistrito;
    private Integer idTrabajador;

    private String guiaRemisionBase64;
}