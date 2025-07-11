// src/main/java/edu/pe/demale/demale_proyecto/models/Conductor.java
package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;

@Entity
@Table(name = "conductor")
public class Conductor {

    @OneToOne
    @MapsId // Uses the ID of Persona as the primary key for Conductor
    @JoinColumn(name = "IdConductor") // Maps to the column named IdConductor
    private Persona persona;

    @Id
    @Column(name = "IdConductor")
    private Integer id; // This field is managed by JPA and linked to Persona's ID

    @Column(name = "Licencia", nullable = false, unique = true) // Licencia must be unique
    private String licencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRol", nullable = false)
    private Rol rol;

    public Conductor() {}

    public Conductor(Persona persona, String licencia, Rol rol) {
        this.persona = persona;
        this.licencia = licencia;
        this.rol = rol;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}