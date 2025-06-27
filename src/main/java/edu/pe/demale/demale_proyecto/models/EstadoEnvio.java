package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;

@Entity
@Table(name = "estadosEnvio") // Asegúrate que el nombre de la tabla coincida
public class EstadoEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEstadoEnvio") // Nombre de la columna PK en DB
    private Integer idEstadoEnvio;

    @Column(name = "Estado", nullable = false, length = 50) // Por ejemplo: "En Almacén", "En Tránsito", "Entregado"
    private String estado;


    // Getters y Setters
    public Integer getIdEstadoEnvio() {
        return idEstadoEnvio;
    }

    public void setIdEstadoEnvio(Integer idEstadoEnvio) {
        this.idEstadoEnvio = idEstadoEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}