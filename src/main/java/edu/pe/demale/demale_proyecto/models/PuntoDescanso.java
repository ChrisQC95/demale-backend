package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;

@Entity
@Table(name = "puntosDescanso") // Mapea a la tabla puntosDescanso
public class PuntoDescanso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPuntoDescanso")
    private Integer idPuntoDescanso;

    @Column(name = "PuntoDescanso", nullable = false, length = 50, unique = true)
    private String nombrePuntoDescanso; // Usamos nombrePuntoDescanso para evitar conflicto con el nombre de la tabla

    @Column(name = "Observacion", length = 128)
    private String observacion;

    // Constructor sin argumentos (necesario para JPA)
    public PuntoDescanso() {
    }

    // Constructor con todos los campos (opcional)
    public PuntoDescanso(Integer idPuntoDescanso, String nombrePuntoDescanso, String observacion) {
        this.idPuntoDescanso = idPuntoDescanso;
        this.nombrePuntoDescanso = nombrePuntoDescanso;
        this.observacion = observacion;
    }

    // --- Getters y Setters ---
    public Integer getIdPuntoDescanso() {
        return idPuntoDescanso;
    }

    public void setIdPuntoDescanso(Integer idPuntoDescanso) {
        this.idPuntoDescanso = idPuntoDescanso;
    }

    public String getNombrePuntoDescanso() {
        return nombrePuntoDescanso;
    }

    public void setNombrePuntoDescanso(String nombrePuntoDescanso) {
        this.nombrePuntoDescanso = nombrePuntoDescanso;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}