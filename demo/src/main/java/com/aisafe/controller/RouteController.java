package com.aisafe.controller;

import com.aisafe.application.route.*;
import com.aisafe.model.Route;
import com.aisafe.model.RouteHistory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
@Tag(
        name = "Flight Routes",
        description = "Gestão de Rotas de Voo (US110, US111, US112, US113, US114)"
)
public class RouteController {

    private final CreateRouteUseCase createRouteUseCase;
    private final GetRouteByIdUseCase getRouteByIdUseCase;
    private final GetRoutesFromAirportUseCase getRoutesFromAirportUseCase;
    private final SearchRoutesUseCase searchRoutesUseCase;
    private final UpdateRouteUseCase updateRouteUseCase;
    private final DeactivateRouteUseCase deactivateRouteUseCase;
    private final GetRouteHistoryUseCase getRouteHistoryUseCase;

    public RouteController(CreateRouteUseCase createRouteUseCase,
                           GetRouteByIdUseCase getRouteByIdUseCase,
                           GetRoutesFromAirportUseCase getRoutesFromAirportUseCase,
                           SearchRoutesUseCase searchRoutesUseCase,
                           UpdateRouteUseCase updateRouteUseCase,
                           DeactivateRouteUseCase deactivateRouteUseCase,
                           GetRouteHistoryUseCase getRouteHistoryUseCase) {
        this.createRouteUseCase = createRouteUseCase;
        this.getRouteByIdUseCase = getRouteByIdUseCase;
        this.getRoutesFromAirportUseCase = getRoutesFromAirportUseCase;
        this.searchRoutesUseCase = searchRoutesUseCase;
        this.updateRouteUseCase = updateRouteUseCase;
        this.deactivateRouteUseCase = deactivateRouteUseCase;
        this.getRouteHistoryUseCase = getRouteHistoryUseCase;
    }

    @Operation(summary = "Criar Rota")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Route createRoute(@RequestBody CreateRouteRequest request) {
        return createRouteUseCase.execute(
                request.originAirport,
                request.destinationAirport,
                request.estimatedFlightTimeMinutes,
                request.minimumRange,
                request.minimumCapacity
        );
    }

    @Operation(summary = "Obter Rota por ID")
    @GetMapping("/{id}")
    public Route getRouteById(@PathVariable Long id) {
        return getRouteByIdUseCase.execute(id);
    }

    @Operation(summary = "Listar Rotas por Aeroporto de Origem")
    @GetMapping("/from/{iataCode}")
    public List<Route> getRoutesFromAirport(@PathVariable String iataCode) {
        return getRoutesFromAirportUseCase.execute(iataCode);
    }

    @Operation(summary = "Pesquisar Rotas")
    @GetMapping("/search")
    public List<Route> searchRoutes(@RequestParam(required = false) String origin,
                                    @RequestParam(required = false) String destination) {
        return searchRoutesUseCase.execute(origin, destination);
    }

    @Operation(summary = "Atualizar Rota")
    @PutMapping("/{id}")
    public Route updateRoute(@PathVariable Long id,
                             @RequestBody UpdateRouteRequest request) {
        return updateRouteUseCase.execute(
                id,
                request.estimatedFlightTimeMinutes,
                request.minimumRange,
                request.minimumCapacity
        );
    }

    @Operation(summary = "Desativar Rota")
    @PatchMapping("/{id}/deactivate")
    public Route deactivateRoute(@PathVariable Long id) {
        return deactivateRouteUseCase.execute(id);
    }

    @Operation(summary = "Ver Histórico da Rota")
    @GetMapping("/{id}/history")
    public List<RouteHistory> getRouteHistory(@PathVariable Long id) {
        return getRouteHistoryUseCase.execute(id);
    }

    public static class CreateRouteRequest {
        public String originAirport;
        public String destinationAirport;
        public Integer estimatedFlightTimeMinutes;
        public Integer minimumRange;
        public Integer minimumCapacity;
    }

    public static class UpdateRouteRequest {
        public Integer estimatedFlightTimeMinutes;
        public Integer minimumRange;
        public Integer minimumCapacity;
    }
}