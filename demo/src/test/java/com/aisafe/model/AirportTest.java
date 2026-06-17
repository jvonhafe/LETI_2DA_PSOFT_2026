package com.aisafe.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários - Airport (Aggregate Root)")
class AirportTest {

    private final IataCode iataValido = new IataCode("LIS");

    @Test
    @DisplayName("Deve instanciar um aeroporto com sucesso se todas as propriedades e estado forem válidos")
    void deveCriarAeroportoValido() {
        Airport airport = new Airport(iataValido, "Humberto Delgado", "Lisboa", "Portugal", "GMT", "OPERATIONAL");

        assertNotNull(airport);
        assertEquals(iataValido, airport.getIataCode());
        assertEquals("OPERATIONAL", airport.getStatus());
    }

    @ParameterizedTest
    @ValueSource(strings = {"OPERATIONAL", "CLOSED", "UNDER_MAINTENANCE"})
    @DisplayName("Deve permitir a transição para qualquer um dos estados de negócio válidos (US109)")
    void devePermitirEstadosValidos(String estadoValido) {
        Airport airport = new Airport(iataValido, "Humberto Delgado", "Lisboa", "Portugal", "GMT", "OPERATIONAL");

        airport.updateStatus(estadoValido);
        assertEquals(estadoValido, airport.getStatus());
    }

    @Test
    @DisplayName("Deve rejeitar a criação do aeroporto se o estado inicial for inválido")
    void naoDevePermitirEstadoInvalidoNaCriacao() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Airport(iataValido, "Humberto Delgado", "Lisboa", "Portugal", "GMT", "INVALID_STATUS");
        }, "Deveria falhar ao tentar inicializar com um estado inválido.");
    }

    @Test
    @DisplayName("Deve rejeitar a alteração de estado e proteger o domínio se o novo estado for inválido ou nulo")
    void naoDevePermitirMudarParaEstadoInvalido() {
        Airport airport = new Airport(iataValido, "Humberto Delgado", "Lisboa", "Portugal", "GMT", "OPERATIONAL");

        // Tentar alterar para nulo
        assertThrows(IllegalArgumentException.class, () -> airport.updateStatus(null));

        // Tentar alterar para estado inventado
        assertThrows(IllegalArgumentException.class, () -> airport.updateStatus("DESTRUIDO"));

        // Garantir que a invariante se manteve segura e o estado continua OPERATIONAL
        assertEquals("OPERATIONAL", airport.getStatus(), "O estado não deveria ter mudado após um input inválido.");
    }
}