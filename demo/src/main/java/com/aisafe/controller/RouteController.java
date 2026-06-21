package com.aisafe.controller;

import com.aisafe.application.route.*;
import com.aisafe.model.IataCode;
import com.aisafe.model.Route;
import com.aisafe.model.RouteHistory;
import com.aisafe.repository.RouteRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@Tag(
        name = "Flight Routes",
        description = "Gestão de Rotas de Voo (Séries 100 e US209)"
)
public class RouteController {

    private final CreateRouteUseCase createRouteUseCase;
    private final GetRouteByIdUseCase getRouteByIdUseCase;
    private final GetRoutesFromAirportUseCase getRoutesFromAirportUseCase;
    private final SearchRouteUseCase searchRoutesUseCase;
    private final UpdateRouteUseCase updateRouteUseCase;
    private final DeactivateRouteUseCase deactivateRouteUseCase;
    private final GetRouteHistoryUseCase getRouteHistoryUseCase;

    private final RouteRepository routeRepository;

    public RouteController(CreateRouteUseCase createRouteUseCase,
                           GetRouteByIdUseCase getRouteByIdUseCase,
                           GetRoutesFromAirportUseCase getRoutesFromAirportUseCase,
                           SearchRouteUseCase searchRoutesUseCase,
                           UpdateRouteUseCase updateRouteUseCase,
                           DeactivateRouteUseCase deactivateRouteUseCase,
                           GetRouteHistoryUseCase getRouteHistoryUseCase,
                           RouteRepository routeRepository) {
        this.createRouteUseCase = createRouteUseCase;
        this.getRouteByIdUseCase = getRouteByIdUseCase;
        this.getRoutesFromAirportUseCase = getRoutesFromAirportUseCase;
        this.searchRoutesUseCase = searchRoutesUseCase;
        this.updateRouteUseCase = updateRouteUseCase;
        this.deactivateRouteUseCase = deactivateRouteUseCase;
        this.getRouteHistoryUseCase = getRouteHistoryUseCase;
        this.routeRepository = routeRepository;
    }

    @Operation(summary = "Criar Rota")
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE')") // Apenas escrita!
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Route createRoute(@RequestBody CreateRouteRequest request) {
        return createRouteUseCase.execute(
                request.originAirport,
                request.destinationAirport,
                request.estimatedFlightTimeMinutes,
                request.minimumRange,
                request.minimumCapacity,
                request.distanceKm
        );
    }

    @Operation(summary = "Obter Rota por ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE', 'ATCC')") // Leitura para todos
    @GetMapping("/{id}")
    public Route getRouteById(@PathVariable Long id) {
        return getRouteByIdUseCase.execute(id);
    }

    @Operation(summary = "Listar Rotas por Aeroporto de Origem")
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE', 'ATCC')")
    @GetMapping("/from/{iataCode}")
    public List<Route> getRoutesFromAirport(@PathVariable String iataCode) {
        return getRoutesFromAirportUseCase.execute(iataCode);
    }

    @Operation(summary = "Pesquisar Rotas")
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE', 'ATCC')")
    @GetMapping("/search")
    public List<Route> searchRoutes(@RequestParam(required = false) String origin,
                                    @RequestParam(required = false) String destination) {
        return searchRoutesUseCase.execute(origin, destination);
    }

    @Operation(summary = "Atualizar Rota")
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE')")
    @PutMapping("/{id}")
    public Route updateRoute(@PathVariable Long id,
                             @RequestBody UpdateRouteRequest request) {
        return updateRouteUseCase.execute(
                id,
                request.estimatedFlightTimeMinutes,
                request.minimumRange,
                request.minimumCapacity,
                request.distanceKm
        );
    }

    @Operation(summary = "Desativar Rota")
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE')")
    @PatchMapping("/{id}/deactivate")
    public Route deactivateRoute(@PathVariable Long id) {
        return deactivateRouteUseCase.execute(id);
    }

    @Operation(summary = "Ver Histórico da Rota")
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE', 'ATCC')")
    @GetMapping("/{id}/history")
    public List<RouteHistory> getRouteHistory(@PathVariable Long id) {
        return getRouteHistoryUseCase.execute(id);
    }

    @Operation(summary = "US209: Consultar rotas que partem ou chegam a um Aeroporto")
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE', 'ATCC')") // Leitura livre
    @GetMapping("/airport/{iata}")
    public List<Route> getRoutesByAirport(@PathVariable String iata) {

        IataCode airportIata = new IataCode(iata);

        return routeRepository.findRoutesByAirport(airportIata);
    }

    public static class CreateRouteRequest {
        public String originAirport;
        public String destinationAirport;
        public Integer estimatedFlightTimeMinutes;
        public Integer minimumRange;
        public Integer minimumCapacity;
        public Double distanceKm;
    }

    public static class UpdateRouteRequest {
        public Integer estimatedFlightTimeMinutes;
        public Integer minimumRange;
        public Integer minimumCapacity;
        public Double distanceKm;
    }
}