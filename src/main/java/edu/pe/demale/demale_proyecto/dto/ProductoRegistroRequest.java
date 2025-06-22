package edu.pe.demale.demale_proyecto.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate; // Usar LocalDate

@Data
public class ProductoRegistroRequest {
    private String producto;
    private BigDecimal alto;
    private BigDecimal ancho;
    private BigDecimal largo;
    private BigDecimal peso;
    private Integer idPuntoAcopio;
    private Integer idTipoProducto;
    private Integer idCliente;
    private Integer idDistrito;
    private Integer idTrabajador;
}