package com.aisafe.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AircraftTest {

    @Test
    void us102_us103_deveCriarAeronaveComDadosValidos() {
        AircraftRegistration reg = new AircraftRegistration("CS-TTO");
        Aircraft aircraft = new Aircraft();
        aircraft.setRegistrationNumber(reg);
        aircraft.setModelId("A320");
        aircraft.setManufacturingDate(LocalDate.now());
        aircraft.setStatus("ACTIVE");

        assertEquals("CS-TTO", aircraft.getRegistrationNumber().getRegistration());
        assertEquals("A320", aircraft.getModelId());
        assertEquals("ACTIVE", aircraft.getStatus());
    }

    @Test
    void us105_deveAlterarOEstadoOperacionalComSucesso() {
        Aircraft aircraft = new Aircraft();
        aircraft.setStatus("ACTIVE");

        // Simula a lógica do teu controlador (passar para maiúsculas e atualizar)
        String novoEstado = "under_maintenance".toUpperCase();
        aircraft.setStatus(novoEstado);

        assertEquals("UNDER_MAINTENANCE", aircraft.getStatus());
    }
}