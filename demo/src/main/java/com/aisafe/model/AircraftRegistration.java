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
        if (registration == null) {
            throw new IllegalArgumentException("A matrícula não pode ser nula.");
        }

        // 🧠 Passa para maiúsculas antes de validar com a Regex!
        String upperReg = registration.toUpperCase().trim();

        // Se o teu grupo quer obrigar o uso do hífen internacional (ex: CS-TTO):
        if (!upperReg.matches("^[A-Z0-9]{1,5}-[A-Z0-9]{1,5}$")) {
            throw new IllegalArgumentException("Matrícula inválida. Deve seguir o formato internacional (ex: CS-TTO).");
        }

        this.registration = upperReg;
    }
}