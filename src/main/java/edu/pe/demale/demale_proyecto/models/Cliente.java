package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(name = "IdCliente")
    private Integer idCliente;

    @OneToOne
    @MapsId // Indica que idCliente es también la clave foránea y su valor se obtendrá de
            // Persona
    @JoinColumn(name = "IdCliente") // La columna FK en la tabla 'cliente' que apunta a 'persona' es 'IdCliente'
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "IdTipoCliente", nullable = false) // IdTipoCliente es siempre requerido
    private TipoCliente tipoCliente;

    @Column(name = "FechaRegistro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "estado", nullable = false)
    private Boolean estado; // Para indicar si el cliente está activo o inactivo

    // Getters y Setters
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}