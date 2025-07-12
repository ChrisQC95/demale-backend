package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;
import java.sql.Date; // Para tipos de fecha SQL
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEnvio")
    private Integer idEnvio;

    @Column(name = "IdConductor", nullable = false)
    private Integer idConductor;

    @Column(name = "IdVehiculo", nullable = false)
    private Integer idVehiculo;

    @Column(name = "IdRuta", nullable = false)
    private Integer idRuta;

    @Column(name = "IdEstadoEnvio", nullable = false)
    private Integer idEstadoEnvio;

    @Column(name = "IdPuntoAcopio", nullable = false)
    private Integer idPuntoAcopio; // Mapea a la nueva columna IdPuntoAcopio

    @Column(name = "IdDistrito", nullable = false)
    private Integer idDistrito;

    @Column(name = "FechSalida", nullable = false)
    private Date fechSalida;

    @Column(name = "FechLlegada")
    private Date fechLlegada;

    @Column(name = "Observacion", length = 255)
    private String observacion;

    // --- Relaciones (Opcional, pero recomendado para una mejor práctica con JPA)
    // ---
    @ManyToOne
    @JoinColumn(name = "IdConductor", insertable = false, updatable = false)
    private Conductor conductor;

    @ManyToOne
    @JoinColumn(name = "IdVehiculo", insertable = false, updatable = false)
    private Vehiculo vehiculo; // Asume que tienes una entidad Vehiculo

    @ManyToOne
    @JoinColumn(name = "IdRuta", insertable = false, updatable = false)
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "IdEstadoEnvio", insertable = false, updatable = false)
    private EstadoEnvio estadoEnvio; // Asume que tienes una entidad EstadoEnvio

    @ManyToOne
    @JoinColumn(name = "IdPuntoAcopio", insertable = false, updatable = false)
    private PuntoAcopio puntoAcopio; // Asume que tienes una entidad PuntoAcopio

    @ManyToOne
    @JoinColumn(name = "IdDistrito", insertable = false, updatable = false)
    private Distrito distrito;

    @OneToMany(mappedBy = "envio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HistorialPuntoDescanso> historialPuntosDescanso;

    public Envio() {
    }

    // Constructor con campos requeridos (o todos, según tu preferencia)
    public Envio(Integer idConductor, Integer idVehiculo, Integer idRuta, Integer idEstadoEnvio, Integer idPuntoAcopio,
            Integer idDistrito, Date fechSalida,
            String observacion) {
        this.idConductor = idConductor;
        this.idVehiculo = idVehiculo;
        this.idRuta = idRuta;
        this.idEstadoEnvio = idEstadoEnvio;
        this.idPuntoAcopio = idPuntoAcopio;
        this.idDistrito = idDistrito;
        this.fechSalida = fechSalida;
        this.observacion = observacion;
    }

    // --- Getters y Setters ---
    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }

    public Integer getIdEstadoEnvio() {
        return idEstadoEnvio;
    }

    public void setIdEstadoEnvio(Integer idEstadoEnvio) {
        this.idEstadoEnvio = idEstadoEnvio;
    }

    public Integer getIdPuntoAcopio() {
        return idPuntoAcopio;
    }

    public void setIdPuntoAcopio(Integer idPuntoAcopio) {
        this.idPuntoAcopio = idPuntoAcopio;
    }

    public Integer getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(Integer idDistrito) {
        this.idDistrito = idDistrito;
    }

    public Date getFechSalida() {
        return fechSalida;
    }

    public void setFechSalida(Date fechSalida) {
        this.fechSalida = fechSalida;
    }

    public Date getFechLlegada() {
        return fechLlegada;
    }

    public void setFechLlegada(Date fechLlegada) {
        this.fechLlegada = fechLlegada;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    // Getters y setters para las relaciones (opcional, si las usas)
    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public EstadoEnvio getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(EstadoEnvio estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public PuntoAcopio getPuntoAcopio() {
        return puntoAcopio;
    }

    public void setPuntoAcopio(PuntoAcopio puntoAcopio) {
        this.puntoAcopio = puntoAcopio;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public List<HistorialPuntoDescanso> getHistorialPuntosDescanso() {
        return historialPuntosDescanso;
    }

    public void setHistorialPuntosDescanso(List<HistorialPuntoDescanso> historialPuntosDescanso) {
        this.historialPuntosDescanso = historialPuntosDescanso;
    }
}