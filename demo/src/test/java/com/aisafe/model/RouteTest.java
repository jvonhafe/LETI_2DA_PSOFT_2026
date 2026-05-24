package com.aisafe.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RouteTest {

    @Test
    void us106_deveCriarEValidarCodigosIataDaRota() {
        IataCode origin = new IataCode("LIS");
        IataCode destination = new IataCode("OPO");

        assertEquals("LIS", origin.getCode());
        assertEquals("OPO", destination.getCode());
        assertNotEquals(origin.getCode(), destination.getCode(), "A origem e o destino da rota devem ser diferentes.");
    }

    @Test
    void us108_quandoCodigoIataInvalido_deveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> {
            new IataCode("INVALIDO");
        }, "Deve falhar porque códigos IATA só podem ter 3 letras.");
    }
}