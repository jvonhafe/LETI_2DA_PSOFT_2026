package com.aisafe.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "airports")
public class Airport {

    @EmbeddedId
    private IataCode iataCode; //value object

    private String name;
    private String city;
    private String country;
    private String timezone;
    private String status;

    protected Airport() {}

    // Construtor
    public Airport(IataCode iataCode, String name, String city, String country, String timezone, String status) {
        this.iataCode = iataCode;
        this.name = name;
        this.city = city;
        this.country = country;
        this.timezone = timezone;
        this.updateStatus(status);
    }

    public void updateStatus(String newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("O estado do aeroporto não pode ser nulo.");
        }
        String statusUpper = newStatus.toUpperCase();
        if (!statusUpper.matches("^(OPERATIONAL|CLOSED|UNDER_MAINTENANCE)$")) {
            throw new IllegalArgumentException("Estado inválido. Escolha: OPERATIONAL, CLOSED, UNDER_MAINTENANCE");
        }
        this.status = statusUpper;
    }

    // Getters
    public IataCode getIataCode() { return iataCode; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getTimezone() { return timezone; }
    public String getStatus() { return status; }
}