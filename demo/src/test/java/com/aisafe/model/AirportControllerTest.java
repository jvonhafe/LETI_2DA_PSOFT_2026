package com.aisafe.model;

import com.aisafe.application.airport.RegisterAirportUseCase;
import com.aisafe.application.airport.RegisterDetailedAirportUseCase;
import com.aisafe.application.airport.UpdateAirportDetailsUseCase;
import com.aisafe.application.airport.UpdateAirportStatusUseCase;
import com.aisafe.controller.AirportController;
import com.aisafe.repository.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários - AirportController (Todas as US do Aeroporto)")
public class AirportControllerTest {

    @Mock
    private RegisterAirportUseCase registerAirportUseCase;

    @Mock
    private UpdateAirportStatusUseCase updateAirportStatusUseCase;

    @Mock
    private RegisterDetailedAirportUseCase registerDetailedAirportUseCase;

    @Mock
    private UpdateAirportDetailsUseCase updateAirportDetailsUseCase;

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportController controller;

    private Airport mockAirport;

    @BeforeEach
    void setUp() {
        // Truque para o Spring HATEOAS (linkTo) não dar erro em testes unitários sem servidor a correr
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Aeroporto padrão para usar nos testes
        mockAirport = new Airport(new IataCode("OPO"), "Sá Carneiro", "Porto", "Portugal", "GMT", "OPERATIONAL");
    }

    // ==========================================
    // TESTES US BASE (106, 107, 109)
    // ==========================================

    @Test
    @DisplayName("US106: Deve registar um aeroporto simples com sucesso")
    void testUS106_createAirport_ReturnsCreatedAirport() {
        // Arrange
        when(registerAirportUseCase.execute(any(Airport.class))).thenReturn(mockAirport);

        // Act
        Airport result = controller.createAirport(mockAirport);

        // Assert
        assertNotNull(result);
        assertEquals("OPO", result.getIataCode().getCode());
        verify(registerAirportUseCase, times(1)).execute(mockAirport);
    }

    @Test
    @DisplayName("US107: Deve retornar a lista de todos os aeroportos")
    void testUS107_getAllAirports_ReturnsList() {
        // Arrange
        when(airportRepository.findAll()).thenReturn(List.of(mockAirport));

        // Act
        List<Airport> result = controller.getAllAirports();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(airportRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("US107: Deve obter um aeroporto por IATA Code com links HATEOAS")
    void testUS107_getAirportById_ReturnsEntityModel() {
        // Arrange
        when(airportRepository.findById(any(IataCode.class))).thenReturn(Optional.of(mockAirport));

        // Act
        EntityModel<Airport> result = controller.getAirportById("OPO");

        // Assert
        assertNotNull(result);
        assertNotNull(result.getContent());
        assertEquals("OPO", result.getContent().getIataCode().getCode());
        assertTrue(result.hasLinks(), "O modelo deve conter links HATEOAS");
    }

    @Test
    @DisplayName("US109: Deve atualizar o estado do aeroporto")
    void testUS109_updateAirportStatus_ReturnsUpdatedAirport() {
        // Arrange
        Airport closedAirport = new Airport(new IataCode("OPO"), "Sá Carneiro", "Porto", "Portugal", "GMT", "CLOSED");
        when(updateAirportStatusUseCase.execute("OPO", "CLOSED")).thenReturn(closedAirport);

        // Act
        Airport result = controller.updateAirportStatus("OPO", "CLOSED");

        // Assert
        assertNotNull(result);
        assertEquals("CLOSED", result.getStatus());
        verify(updateAirportStatusUseCase, times(1)).execute("OPO", "CLOSED");
    }

    // ==========================================
    // TESTES US ESPECÍFICAS (207, 208)
    // ==========================================

    @Test
    @DisplayName("US207: Deve registar um aeroporto detalhado (com instalações e imagens) com sucesso")
    void testUS207_createDetailedAirport_ReturnsCreatedAirport() {
        // Arrange
        RegisterDetailedAirportUseCase.DetailedAirportDto dto = new RegisterDetailedAirportUseCase.DetailedAirportDto();
        dto.iataCode = "OPO";
        dto.name = "Sá Carneiro";

        when(registerDetailedAirportUseCase.execute(any(RegisterDetailedAirportUseCase.DetailedAirportDto.class))).thenReturn(mockAirport);

        // Act
        Airport result = controller.createDetailedAirport(dto);

        // Assert
        assertNotNull(result);
        assertEquals("OPO", result.getIataCode().getCode());
        verify(registerDetailedAirportUseCase, times(1)).execute(dto);
    }

    @Test
    @DisplayName("US208: Deve atualizar os detalhes (contactos/horários) do aeroporto com sucesso")
    void testUS208_updateAirportDetails_ReturnsUpdatedAirport() {
        // Arrange
        UpdateAirportDetailsUseCase.UpdateDetailsRequest request = new UpdateAirportDetailsUseCase.UpdateDetailsRequest();

        when(updateAirportDetailsUseCase.execute(eq("OPO"), any(UpdateAirportDetailsUseCase.UpdateDetailsRequest.class))).thenReturn(mockAirport);

        // Act
        Airport result = controller.updateAirportDetails("OPO", request);

        // Assert
        assertNotNull(result);
        verify(updateAirportDetailsUseCase, times(1)).execute("OPO", request);
    }

    // ==========================================
    // TESTES US EXTRA / CQRS (108, 210, 211)
    // ==========================================

    @Test
    @DisplayName("US108: Deve retornar aeroportos que correspondam à cidade pesquisada")
    void testUS108_searchAirports_ReturnsMatchingCity() {
        // Arrange
        when(airportRepository.findByCityContainingIgnoreCase("Porto")).thenReturn(List.of(mockAirport));

        // Act
        List<Airport> result = controller.searchAirports("Porto");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Porto", result.get(0).getCity());
        verify(airportRepository, times(1)).findByCityContainingIgnoreCase("Porto");
    }

    @Test
    @DisplayName("US210: Deve retornar a lista de aeroportos mais movimentados com o limite correto")
    void testUS210_getBusiestAirports_AppliesLimitCorrectly() {
        // Arrange
        BusiestAirportDto mockDto = mock(BusiestAirportDto.class);
        when(airportRepository.findBusiestAirports(any(PageRequest.class))).thenReturn(List.of(mockDto, mockDto));

        // Act
        int limit = 2;
        List<BusiestAirportDto> result = controller.getBusiestAirports(limit);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(airportRepository, times(1)).findBusiestAirports(PageRequest.of(0, limit));
    }

    @Test
    @DisplayName("US211: Deve agrupar os aeroportos corretamente pelo país (country)")
    void testUS211_getAirportsGrouped_ByCountry() {
        // Arrange
        Airport a1 = new Airport(new IataCode("OPO"), "A1", "Porto", "Portugal", "GMT", "OPERATIONAL");
        Airport a2 = new Airport(new IataCode("LIS"), "A2", "Lisboa", "Portugal", "GMT", "OPERATIONAL");
        Airport a3 = new Airport(new IataCode("CDG"), "A3", "Paris", "France", "CET", "OPERATIONAL");

        when(airportRepository.findAll()).thenReturn(List.of(a1, a2, a3));

        // Act
        Map<String, List<Airport>> result = controller.getAirportsGrouped("country");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size(), "Devem existir 2 grupos: Portugal e France");
        assertTrue(result.containsKey("Portugal"));
        assertTrue(result.containsKey("France"));
        assertEquals(2, result.get("Portugal").size());
        assertEquals(1, result.get("France").size());
    }

    @Test
    @DisplayName("US211: Deve lançar excepção se o parâmetro de agrupamento for inválido")
    void testUS211_getAirportsGrouped_InvalidParameter_ThrowsException() {
        // Arrange
        when(airportRepository.findAll()).thenReturn(List.of());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controller.getAirportsGrouped("parametro_inventado")
        );

        assertTrue(exception.getMessage().contains("não suportado"));
    }
}