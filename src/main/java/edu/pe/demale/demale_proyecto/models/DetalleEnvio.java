package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;

@Entity
@Table(name = "detalleenvios")
public class DetalleEnvio {

    @EmbeddedId // Indica que la clave primaria es embebida
    private DetalleEnvioId id; // Usa la clase de clave compuesta

    // --- Relaciones ---
    @ManyToOne
    @MapsId("idEnvio") // Mapea la parte 'idEnvio' de la clave compuesta
    @JoinColumn(name = "IdEnvio", referencedColumnName = "IdEnvio")
    private Envio envio;

    @ManyToOne
    @MapsId("idProducto") // Mapea la parte 'idProducto' de la clave compuesta
    @JoinColumn(name = "IdProducto", referencedColumnName = "IdProducto")
    private Productos producto; // Asume que tienes una entidad Producto

    // Constructor sin argumentos
    public DetalleEnvio() {
        this.id = new DetalleEnvioId();
    }

    // Constructor para facilitar la creación
    public DetalleEnvio(Integer idEnvio, Integer idProducto) {
        this.id = new DetalleEnvioId(idEnvio, idProducto);
    }

    // --- Getters y Setters ---
    public DetalleEnvioId getId() {
        return id;
    }

    public void setId(DetalleEnvioId id) {
        this.id = id;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
        if (this.id != null) {
            this.id.setIdEnvio(envio != null ? envio.getIdEnvio() : null);
        }
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
        if (this.id != null) {
            this.id.setIdProducto(producto != null ? producto.getIdProducto() : null);
        }
    }
}