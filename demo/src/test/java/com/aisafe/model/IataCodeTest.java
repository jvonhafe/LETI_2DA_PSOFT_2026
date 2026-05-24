package com.aisafe.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários - IataCode (Value Object)")
class IataCodeTest {

    @Test
    @DisplayName("Deve instanciar um código IATA válido com 3 letras maiúsculas")
    void deveCriarIataCodeValido() {
        IataCode iata = new IataCode("LIS");
        assertNotNull(iata, "O objeto IataCode não deveria ser nulo.");
        assertEquals("LIS", iata.getCode(), "O código armazenado deve ser exatamente 'LIS'.");
    }

    @Test
    @DisplayName("Deve limpar espaços em branco e converter letras minúsculas para maiúsculas")
    void deveLimparEConverterParaMaiusculas() {
        IataCode iata = new IataCode("  opo  ");
        assertEquals("OPO", iata.getCode(), "O construtor deve fazer trim() e toUpperCase().");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o código fornecido for nulo ou puramente vazio")
    void naoDevePermitirIataCodeNuloOuVazio() {
        Exception exceptionNulo = assertThrows(IllegalArgumentException.class, () -> new IataCode(null));
        assertTrue(exceptionNulo.getMessage().contains("não pode ser nulo ou vazio"));

        Exception exceptionVazio = assertThrows(IllegalArgumentException.class, () -> new IataCode("   "));
        assertTrue(exceptionVazio.getMessage().contains("não pode ser nulo ou vazio"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"LI", "LISS", "L1S", "L-S", "123"})
    @DisplayName("Deve rejeitar códigos com comprimentos errados ou caracteres não alfabéticos")
    void naoDevePermitirCodigosInvalidos(String codigoInvalido) {
        assertThrows(IllegalArgumentException.class, () -> new IataCode(codigoInvalido),
                "Deveria ter falhado para o código inválido: " + codigoInvalido);
    }

    @Test
    @DisplayName("Deve garantir a igualdade por valor (Value Object) e não por referência de memória")
    void deveGarantirIgualdadePorValor() {
        IataCode iata1 = new IataCode("LIS");
        IataCode iata2 = new IataCode("LIS");

        assertEquals(iata1, iata2, "Dois objetos IataCode com o mesmo código devem ser considerados iguais.");
        assertEquals(iata1.hashCode(), iata2.hashCode(), "Os hashCodes devem ser idênticos.");
    }
}