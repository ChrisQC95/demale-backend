package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdVehiculo")
    private Integer idVehiculo;

    @Column(name = "Placa", nullable = false, unique = true, length = 20)
    private String placa;

    @Column(name = "Marca", nullable = false, length = 50)
    private String marca;

    @Column(name = "Modelo", nullable = false, length = 50)
    private String modelo;

    @Column(name = "Capacidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal capacidad;

    public Vehiculo() {}

    public Vehiculo(Integer idVehiculo, String placa, String marca, String modelo, BigDecimal capacidad) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidad = capacidad;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigDecimal getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(BigDecimal capacidad) {
        this.capacidad = capacidad;
    }
}
