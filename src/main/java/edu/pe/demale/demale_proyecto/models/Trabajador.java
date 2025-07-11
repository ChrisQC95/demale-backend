package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate; // Para FechNacimiento

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trabajador")
public class Trabajador {

    @Id // IdTrabajador es la PK
    @Column(name = "IdTrabajador") // Nombre de la columna PK
    private Integer idTrabajador;

    @OneToOne // Un trabajador tiene una Persona asociada
    @MapsId // Indica que idTrabajador es también la clave foránea y su valor se obtendrá de
    @JoinColumn(name = "IdTrabajador") // La columna FK en 'trabajador' que apunta a 'persona' es 'IdTrabajador'
    private Persona persona; // El trabajador es una Persona

    @Column(name = "FechNacimiento", nullable = false) // Mapeo de FechNacimiento
    private LocalDate fechNacimiento;

    @Column(name = "Foto", length = 255) // Mapeo de Foto, es nullable
    private String foto; // Ruta o URL de la foto

    @ManyToOne // Un trabajador tiene un Rol
    @JoinColumn(name = "IdRol", nullable = false) // Columna FK a la tabla Rol
    private Rol rol;

    // Asumo un campo 'activo' o similar, si no existe en DB, puedes omitirlo
    // @Column(name = "Activo", nullable = false)
    // private Boolean activo;

    // Getters y Setters
    public Integer getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Integer idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public LocalDate getFechNacimiento() {
        return fechNacimiento;
    }

    public void setFechNacimiento(LocalDate fechNacimiento) {
        this.fechNacimiento = fechNacimiento;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // public Boolean getActivo() { // Si tienes el campo 'activo'
    // return activo;
    // }
    //
    // public void setActivo(Boolean activo) { // Si tienes el campo 'activo'
    // this.activo = activo;
    // }
}