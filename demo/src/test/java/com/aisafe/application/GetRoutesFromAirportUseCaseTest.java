package com.aisafe.application;

import com.aisafe.application.route.GetRoutesFromAirportUseCase;
import com.aisafe.application.route.RouteUseCaseHelper;
import com.aisafe.model.Airport;
import com.aisafe.model.IataCode;
import com.aisafe.model.Route;
import com.aisafe.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetRoutesFromAirportUseCaseTest {

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private RouteUseCaseHelper helper;

    @InjectMocks
    private GetRoutesFromAirportUseCase useCase;

    private Airport originAirport;
    private Route mockRoute;

    @BeforeEach
    void setUp() {
        originAirport = new Airport(new IataCode("OPO"), "Sá Carneiro", "Porto", "Portugal", "GMT", "OPERATIONAL");
        mockRoute = new Route(); // Rota fictícia para o teste
    }

    @Test
    void execute_WhenAirportExists_ReturnsRoutesList() {
        // Arrange (Preparar)
        String originCode = "OPO";
        when(helper.getAirport(originCode)).thenReturn(originAirport);
        when(routeRepository.findByOriginAirport(originAirport)).thenReturn(List.of(mockRoute));

        // Act (Executar)
        List<Route> result = useCase.execute(originCode);

        // Assert (Verificar)
        assertNotNull(result);
        assertEquals(1, result.size());

        // Verifica se o helper e o repositório foram chamados corretamente
        verify(helper, times(1)).getAirport(originCode);
        verify(routeRepository, times(1)).findByOriginAirport(originAirport);
    }
}