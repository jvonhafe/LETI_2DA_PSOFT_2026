package com.aisafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class AircraftRegistration {

    @Column(name = "registration_number")
    private String registration;

    protected AircraftRegistration() {}

    public AircraftRegistration(String registration) {
        if (registration == null || !registration.matches("^[A-Z0-9]{1,5}-[A-Z0-9]{1,5}$|^[A-Z0-9]{3,7}$")) {
            throw new IllegalArgumentException("O formato da matrícula é inválido. Exemplo correto: CS-TTO");
        }
        this.registration = registration.toUpperCase();
    }
}