package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;
import java.time.LocalDateTime; // Usaremos LocalDateTime para fecha y hora

@Entity
@Table(name = "historial_puntos_descanso")
public class HistorialPuntoDescanso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdHistorialDescanso")
    private Integer idHistorialDescanso;

    // Relación ManyToOne con Envio
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading es eficiente para relaciones many-to-one
    @JoinColumn(name = "IdEnvio", nullable = false)
    private Envio envio;

    // Relación ManyToOne con PuntoDescanso
    @ManyToOne(fetch = FetchType.EAGER) // Eager loading para el nombre del punto de descanso en el historial
    @JoinColumn(name = "IdPuntoDescanso", nullable = false)
    private PuntoDescanso puntoDescanso;

    @Column(name = "FechaHoraRegistro", nullable = false)
    private LocalDateTime fechaHoraRegistro; // Fecha y hora del registro

    // Constructor sin argumentos
    public HistorialPuntoDescanso() {
    }

    // Constructor con campos requeridos
    public HistorialPuntoDescanso(Envio envio, PuntoDescanso puntoDescanso, LocalDateTime fechaHoraRegistro) {
        this.envio = envio;
        this.puntoDescanso = puntoDescanso;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    // --- Getters y Setters ---
    public Integer getIdHistorialDescanso() {
        return idHistorialDescanso;
    }

    public void setIdHistorialDescanso(Integer idHistorialDescanso) {
        this.idHistorialDescanso = idHistorialDescanso;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }

    public PuntoDescanso getPuntoDescanso() {
        return puntoDescanso;
    }

    public void setPuntoDescanso(PuntoDescanso puntoDescanso) {
        this.puntoDescanso = puntoDescanso;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
}