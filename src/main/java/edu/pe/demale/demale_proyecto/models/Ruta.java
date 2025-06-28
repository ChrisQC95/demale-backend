package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*; // Usa jakarta.persistence si tu Spring Boot es 3.x o superior

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "rutas") // Mapea esta entidad a la tabla 'rutas' en la base de datos
public class Ruta {

    @Id // Marca esta propiedad como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el IdRuta es auto_increment
    @Column(name = "IdRuta") // Mapea a la columna IdRuta
    private Integer idRuta;

    @Column(name = "SerialRuta", nullable = false, length = 15, unique = true) // Mapea a la columna SerialRuta
    private String serialRuta;

    @Column(name = "Ruta", nullable = false, length = 50) // Mapea a la columna Ruta
    private String nombreRuta; // 'Ruta' es un nombre de columna, así que usamos 'nombreRuta' para el campo

    @Column(name = "Glosa", length = 100) // Mapea a la columna Glosa
    private String glosa;

    // Constructor sin argumentos (necesario para JPA)
    public Ruta() {
    }

    // Constructor con todos los campos (opcional)
    public Ruta(Integer idRuta, String serialRuta, String nombreRuta, String glosa) {
        this.idRuta = idRuta;
        this.serialRuta = serialRuta;
        this.nombreRuta = nombreRuta;
        this.glosa = glosa;
    }

    // --- Getters y Setters ---

    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }

    public String getSerialRuta() {
        return serialRuta;
    }

    public void setSerialRuta(String serialRuta) {
        this.serialRuta = serialRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }
}