package com.aisafe.controller;

import com.aisafe.model.Route;
import com.aisafe.model.RouteHistory;
import com.aisafe.service.RouteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")

@Tag(
        name = "Flight Routes, Flight Operations",
        description = "Gestão de Rotas de Voo (US110, US111, US112, US113, US114)"
)
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService){
        this.routeService = routeService;
    }


    @Operation(summary = "Criar Rota")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Route createRoute(@RequestBody CreateRouteRequest request){

        return routeService.createRoute(
                request.originAirport,
                request.destinationAirport,
                request.estimatedFlightTimeMinutes,
                request.minimumRange,
                request.minimumCapacity
        );
    }


    @Operation(summary = "Obter Rota por ID")
    @GetMapping("/{id}")
    public Route getRouteById(@PathVariable Long id){

        return routeService.getRouteById(id);
    }


    @Operation(summary = "Listar Rotas por Aeroporto de Origem")
    @GetMapping("/from/{iataCode}")
    public List<Route> getRoutesFromAirport(
            @PathVariable String iataCode){

        return routeService.getRoutesFromAirport(iataCode);
    }


    @Operation(summary = "Pesquisar Rotas")
    @GetMapping("/search")
    public List<Route> searchRoutes(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination){

        return routeService.searchRoutes(origin,destination);
    }


    @Operation(summary = "Atualizar Rota")
    @PutMapping("/{id}")
    public Route updateRoute(
            @PathVariable Long id,
            @RequestBody UpdateRouteRequest request){

        return routeService.updateRoute(
                id,
                request.estimatedFlightTimeMinutes,
                request.minimumRange,
                request.minimumCapacity
        );
    }


    @Operation(summary = "Desativar Rota")
    @PatchMapping("/{id}/deactivate")
    public Route deactivateRoute(
            @PathVariable Long id){

        return routeService.deactivateRoute(id);
    }


    @Operation(summary = "Ver Histórico da Rota")
    @GetMapping("/{id}/history")
    public List<RouteHistory> getRouteHistory(
            @PathVariable Long id){

        return routeService.getRouteHistory(id);
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