package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipoProductos") // Asegúrate que el nombre de la tabla coincida (tipoProductos o tipoProducto)
public class TipoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTipoProducto") // Nombre de la columna PK en DB
    private Integer idTipoProducto;

    @Column(name = "TipoProducto", nullable = false, length = 50) // Asumo un nombre para el tipo de producto
    private String tipoProducto; // Por ejemplo: "Electrónica", "Ropa", "Muebles"

    // Getters y Setters
    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
}