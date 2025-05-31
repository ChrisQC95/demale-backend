package edu.pe.demale.demale_proyecto.models;

import java.sql.Date;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdProducto;
    @Column
    private String Producto;
    @Column
    private double Alto;
    @Column
    private double Ancho;
    @Column
    private double Largo;
    @Column
    private double Peso;
    @Column
    private Date FechIngreso;
    @Column
    private Date FechLlegada;
    @Column
    private int IdPuntoAcopio;
    @Column
    private int IdTipoProducto;
    @Column
    private int IdCliente;
    @Column
    private int IdEstadoEnvio;
    @Column
    private int IdDistrito;

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public double getAlto() {
        return Alto;
    }

    public void setAlto(double alto) {
        Alto = alto;
    }

    public double getAncho() {
        return Ancho;
    }

    public void setAncho(double ancho) {
        Ancho = ancho;
    }

    public double getLargo() {
        return Largo;
    }

    public void setLargo(double largo) {
        Largo = largo;
    }

    public double getPeso() {
        return Peso;
    }

    public void setPeso(double peso) {
        Peso = peso;
    }

    public Date getFechIngreso() {
        return FechIngreso;
    }

    public void setFechIngreso(Date fechIngreso) {
        FechIngreso = fechIngreso;
    }

    public Date getFechLlegada() {
        return FechLlegada;
    }

    public void setFechLlegada(Date fechLlegada) {
        FechLlegada = fechLlegada;
    }

    public int getIdPuntoAcopio() {
        return IdPuntoAcopio;
    }

    public void setIdPuntoAcopio(int idPuntoAcopio) {
        IdPuntoAcopio = idPuntoAcopio;
    }

    public int getIdTipoProducto() {
        return IdTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        IdTipoProducto = idTipoProducto;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        IdCliente = idCliente;
    }

    public int getIdEstadoEnvio() {
        return IdEstadoEnvio;
    }

    public void setIdEstadoEnvio(int idEstadoEnvio) {
        IdEstadoEnvio = idEstadoEnvio;
    }

    public int getIdDistrito() {
        return IdDistrito;
    }

    public void setIdDistrito(int idDistrito) {
        IdDistrito = idDistrito;
    }

}
