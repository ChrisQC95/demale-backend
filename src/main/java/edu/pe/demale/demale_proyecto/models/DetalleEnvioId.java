package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects; // Para implementar equals y hashCode

@Embeddable
public class DetalleEnvioId implements Serializable {

    private Integer idEnvio;
    private Integer idProducto;

    public DetalleEnvioId() {
    }

    public DetalleEnvioId(Integer idEnvio, Integer idProducto) {
        this.idEnvio = idEnvio;
        this.idProducto = idProducto;
    }

    // --- Getters y Setters ---
    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    // Implementar equals y hashCode es CRUCIAL para claves compuestas
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DetalleEnvioId that = (DetalleEnvioId) o;
        return Objects.equals(idEnvio, that.idEnvio) &&
                Objects.equals(idProducto, that.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEnvio, idProducto);
    }
}