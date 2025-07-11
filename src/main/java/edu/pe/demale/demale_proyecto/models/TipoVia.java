package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipoVia")
public class TipoVia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTipoVia")
    private Integer idTipoVia;

    @Column(name = "TipoVia", length = 50, nullable = false)
    private String nombreTipoVia;

    // Getters y Setters
    public Integer getIdTipoVia() {
        return idTipoVia;
    }

    public void setIdTipoVia(Integer idTipoVia) {
        this.idTipoVia = idTipoVia;
    }

    public String getNombreTipoVia() {
        return nombreTipoVia;
    }

    public void setNombreTipoVia(String nombreTipoVia) {
        this.nombreTipoVia = nombreTipoVia;
    }
}