package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;

@Entity
@Table(name = "conductor")
public class Conductor {

    @Id
    @Column(name = "IdConductor")
    private Integer idConductor;

    @OneToOne
    @MapsId // Esto hace que use el mismo Id que Persona
    @JoinColumn(name = "IdConductor")
    private Persona persona;

    @Column(name = "Licencia", nullable = false, unique = true)
    private String licencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRol", nullable = false)
    private Rol rol;

    public Conductor() {
    }

    // Constructor con todos los campos (opcional, pero útil)
    public Conductor(Integer idConductor, String licencia, Persona persona, Rol rol) {
        this.idConductor = idConductor;
        this.licencia = licencia;
        this.persona = persona;
        this.rol = rol;
    }
    // Getters y setters

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
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
