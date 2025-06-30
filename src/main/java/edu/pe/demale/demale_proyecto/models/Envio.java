package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;
import java.sql.Date; // Para tipos de fecha SQL

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

    // Si quieres que DetalleEnvio sea bidireccional desde Envio
    // @OneToMany(mappedBy = "envio", cascade = CascadeType.ALL, orphanRemoval =
    // true)
    // private List<DetalleEnvio> detalles;

    // Constructor sin argumentos
    public Envio() {
    }

    // Constructor con campos requeridos (o todos, según tu preferencia)
    public Envio(Integer idConductor, Integer idVehiculo, Integer idRuta, Integer idEstadoEnvio, Date fechSalida,
            String observacion) {
        this.idConductor = idConductor;
        this.idVehiculo = idVehiculo;
        this.idRuta = idRuta;
        this.idEstadoEnvio = idEstadoEnvio;
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
}