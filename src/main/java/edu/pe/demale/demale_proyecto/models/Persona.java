package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPersona")
    private Integer idPersona;

    @Column(name = "Nombres", length = 100, nullable = true)
    private String nombres;

    @Column(name = "Apellidos", length = 100, nullable = true)
    private String apellidos;

    @Column(name = "RazonSocial", length = 200, nullable = true) // <-- NUEVO CAMPO, debe ser nullable=true
    private String razonSocial;

    @Column(name = "DocIdentidad", nullable = false, unique = true) // IdTipoDoc es siempre requerido
    private String numeroDocumento;

    @Column(name = "Telefono", length = 20)
    private String telefono;

    @Column(name = "Correo", length = 100)
    private String correo;

    // Relaciones de dirección (opcionales para el insert rápido)
    @ManyToOne
    @JoinColumn(name = "IdDistrito", nullable = true) // Puede ser NULL
    private Distrito distrito;

    @ManyToOne
    @JoinColumn(name = "IdTipoVia", nullable = true) // Puede ser NULL
    private TipoVia tipoVia;

    @Column(name = "Direccion", length = 255) // Puede ser NULL o vacío
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "IdTipoDoc") // Asegúrate de que coincida (PascalCase)
    private TipoDocumento tipoDocumento;

    @Column(name = "NMunicipal", length = 50) // Puede ser NULL o vacío
    private String nMunicipal;

    // Getters y Setters
    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public TipoVia getTipoVia() {
        return tipoVia;
    }

    public void setTipoVia(TipoVia tipoVia) {
        this.tipoVia = tipoVia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNMunicipal() {
        return nMunicipal;
    }

    public void setNMunicipal(String nMunicipal) {
        this.nMunicipal = nMunicipal;
    }
}