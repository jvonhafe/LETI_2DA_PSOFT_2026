package com.aisafe.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class IataCode implements Serializable {

    private String code;

    protected IataCode() {

    }

    public IataCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("O código IATA não pode ser nulo ou vazio.");
        }
        String cleanCode = code.trim().toUpperCase();
        if (!cleanCode.matches("^[A-Z]{3}$")) {
            throw new IllegalArgumentException("O código IATA tem de ter exatamente 3 letras (ex: LIS, OPO).");
        }
        this.code = cleanCode;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IataCode iataCode = (IataCode) o;
        return Objects.equals(code, iataCode.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}