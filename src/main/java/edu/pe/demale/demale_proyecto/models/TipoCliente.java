package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipoCliente") // Asegúrate de que el nombre de la tabla coincida exactamente con tu DB
public class TipoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTipoCliente")
    private Integer idTipoCliente;

    @Column(name = "TipoCliente", length = 50, nullable = false)
    private String tipoCliente;

    @Column(name = "Glosa", length = 255) // Glosa puede ser null o vacía
    private String glosa;

    // Getters y Setters
    public Integer getIdTipoCliente() {
        return idTipoCliente;
    }

    public void setIdTipoCliente(Integer idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }
}