package edu.pe.demale.demale_proyecto.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_movements")
public class DemaleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campos que corresponden a las columnas del prototipo:
    // CLIENTE
    @Column(name = "client_name", nullable = false)
    private String clientName;

    // PRODUCTO
    @Column(name = "product_name", nullable = false)
    private String productName;

    // CATEGORIA
    @Column(name = "category", nullable = false)
    private String category;

    // FECHA INGRESO
    @Column(name = "entry_date", nullable = false)
    private LocalDateTime entryDate;

    // PUNTO ACOPIO
    @Column(name = "collection_point", nullable = false)
    private String collectionPoint;

    // DESTINO
    @Column(name = "destination", nullable = false)
    private String destination;

    // ESTADO
    @Column(name = "status", nullable = false)
    private String status;

    // CHOFER
    @Column(name = "driver_name")
    private String driverName;

    // VEHICULO
    @Column(name = "vehicle_plate")
    private String vehiclePlate;

    public DemaleModel() {
    }

    public DemaleModel(String clientName, String productName, String category, LocalDateTime entryDate,
            String collectionPoint, String destination, String status, String driverName, String vehiclePlate) {
        this.clientName = clientName;
        this.productName = productName;
        this.category = category;
        this.entryDate = entryDate;
        this.collectionPoint = collectionPoint;
        this.destination = destination;
        this.status = status;
        this.driverName = driverName;
        this.vehiclePlate = vehiclePlate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }
}