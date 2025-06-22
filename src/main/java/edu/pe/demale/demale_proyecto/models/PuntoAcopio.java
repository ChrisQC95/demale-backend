package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;

@Entity
@Table(name = "puntoAcopio") // Asegúrate que el nombre de la tabla coincida
public class PuntoAcopio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPuntoAcopio") // Nombre de la columna PK en DB
    private Integer idPuntoAcopio;

    @Column(name = "NombreAcopio", nullable = false, length = 100) // Asumo un nombre para el punto de acopio
    private String nombreAcopio;

    // Puedes añadir otros campos si tu tabla puntoAcopio los tiene, por ejemplo:
    // @Column(name = "Direccion")
    // private String direccion;

    // Getters y Setters
    public Integer getIdPuntoAcopio() {
        return idPuntoAcopio;
    }

    public void setIdPuntoAcopio(Integer idPuntoAcopio) {
        this.idPuntoAcopio = idPuntoAcopio;
    }

    public String getNombreAcopio() {
        return nombreAcopio;
    }

    public void setNombreAcopio(String nombreAcopio) {
        this.nombreAcopio = nombreAcopio;
    }
}