package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;
import java.math.BigDecimal; // Importar para decimal, que es más preciso que double para monetario/medidas
import java.time.LocalDate; // Usar LocalDate para fechas modernas
// NO ES NECESARIO java.sql.Date si usas LocalDate

@Entity
@Table(name = "producto") // Asegúrate de que el nombre de la tabla sea 'producto' (singular)
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdProducto") // Asegúrate que el nombre de la columna sea IdProducto
    private Integer idProducto; // Usar Integer para permitir nulls si auto_increment falla o en otros contextos

    @Column(name = "Producto", nullable = false, length = 50) // Asegúrate que el nombre de la columna sea Producto
    private String producto; // Nombre del producto

    @Column(name = "Alto", nullable = false, precision = 10, scale = 2) // Mapeo a decimal(10,2)
    private BigDecimal alto;

    @Column(name = "Ancho", nullable = false, precision = 10, scale = 2) // Mapeo a decimal(10,2)
    private BigDecimal ancho;

    @Column(name = "Largo", nullable = false, precision = 10, scale = 2) // Mapeo a decimal(10,2)
    private BigDecimal largo;

    @Column(name = "Peso", nullable = false, precision = 10, scale = 2) // Mapeo a decimal(10,2)
    private BigDecimal peso;

    @Column(name = "FechIngreso", nullable = false)
    private LocalDate fechIngreso; // Usar LocalDate para java.time

    @Column(name = "FechLlegada") // Puede ser null
    private LocalDate fechLlegada; // Usar LocalDate para java.time

    // --- RELACIONES CON OTRAS ENTIDADES ---
    // Es CRÍTICO que estas sean referencias a las ENTIDADES, no solo al ID int
    // Si no tienes estas clases de entidad aún, deberás crearlas también.

    @ManyToOne // Un producto tiene un PuntoAcopio
    @JoinColumn(name = "IdPuntoAcopio", nullable = false) // Mapea a la columna IdPuntoAcopio en la tabla producto
    private PuntoAcopio puntoAcopio; // Campo de tipo PuntoAcopio

    @ManyToOne // Un producto tiene un TipoProducto
    @JoinColumn(name = "IdTipoProducto", nullable = false)
    private TipoProducto tipoProducto;

    @ManyToOne // Un producto pertenece a un Cliente
    @JoinColumn(name = "IdCliente", nullable = false)
    private Cliente cliente;

    @ManyToOne // Un producto tiene un EstadoEnvio
    @JoinColumn(name = "IdEstadoEnvio", nullable = false)
    private EstadoEnvio estadoEnvio;

    @ManyToOne // Un producto tiene un Distrito de destino
    @JoinColumn(name = "IdDistrito", nullable = false)
    private Distrito distrito;

    // --- NUEVA COLUMNA PARA LA GUÍA DE REMISIÓN ---
    @Lob // Indica que es un Large Object (BLOB o CLOB)
    @Column(name = "guiaRemisión") // Nombre de la columna en la base de datos
    private byte[] guiaRemision; // Para almacenar el PDF (BLOB)

    @ManyToOne // Un producto es registrado por un Trabajador
    @JoinColumn(name = "IdTrabajador", nullable = false) // Asumo que hay una columna IdTrabajador en la tabla producto
    private Trabajador trabajador;

    // --- Getters y Setters ---
    // (Puedes generarlos automáticamente con tu IDE o Lombok)

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public BigDecimal getAlto() {
        return alto;
    }

    public void setAlto(BigDecimal alto) {
        this.alto = alto;
    }

    public BigDecimal getAncho() {
        return ancho;
    }

    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }

    public BigDecimal getLargo() {
        return largo;
    }

    public void setLargo(BigDecimal largo) {
        this.largo = largo;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public LocalDate getFechIngreso() {
        return fechIngreso;
    }

    public void setFechIngreso(LocalDate fechIngreso) {
        this.fechIngreso = fechIngreso;
    }

    public LocalDate getFechLlegada() {
        return fechLlegada;
    }

    public void setFechLlegada(LocalDate fechLlegada) {
        this.fechLlegada = fechLlegada;
    }

    public PuntoAcopio getPuntoAcopio() {
        return puntoAcopio;
    }

    public void setPuntoAcopio(PuntoAcopio puntoAcopio) {
        this.puntoAcopio = puntoAcopio;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EstadoEnvio getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(EstadoEnvio estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public byte[] getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(byte[] guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public Trabajador getTrabajador() { // Getter para trabajador
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) { // Setter para trabajador
        this.trabajador = trabajador;
    }

}