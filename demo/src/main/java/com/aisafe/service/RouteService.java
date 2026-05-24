package com.aisafe.service;

import com.aisafe.core.exception.ResourceNotFoundException;
import com.aisafe.model.*;
import com.aisafe.repository.AirportRepository;
import com.aisafe.repository.RouteHistoryRepository;
import com.aisafe.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final AirportRepository airportRepository;
    private final RouteHistoryRepository routeHistoryRepository;

    public RouteService(RouteRepository routeRepository,
                        AirportRepository airportRepository,
                        RouteHistoryRepository routeHistoryRepository) {
        this.routeRepository = routeRepository;
        this.airportRepository = airportRepository;
        this.routeHistoryRepository = routeHistoryRepository;
    }

    public Route createRoute(String originCode,
                             String destinationCode,
                             Integer estimatedFlightTimeMinutes,
                             Integer minimumRange,
                             Integer minimumCapacity) {

        validateRouteInput(originCode, destinationCode, estimatedFlightTimeMinutes, minimumRange, minimumCapacity);

        Airport origin = getAirport(originCode);
        Airport destination = getAirport(destinationCode);

        // CORREÇÃO 1: Usar .equals() em vez de .equalsIgnoreCase() porque agora são Value Objects
        if (origin.getIataCode().equals(destination.getIataCode())) {
            throw new IllegalArgumentException("Origin and destination airports cannot be the same.");
        }

        if (routeRepository.existsByOriginAirportAndDestinationAirportAndStatus(
                origin, destination, RouteStatus.ACTIVE)) {
            throw new IllegalArgumentException("An active route already exists between these airports.");
        }

        Route route = new Route();
        route.setOriginAirport(origin);
        route.setDestinationAirport(destination);
        route.setEstimatedFlightTimeMinutes(estimatedFlightTimeMinutes);
        route.setMinimumRange(minimumRange);
        route.setMinimumCapacity(minimumCapacity);
        route.setStatus(RouteStatus.ACTIVE);

        Route savedRoute = routeRepository.save(route);
        saveHistory(savedRoute, "CREATED", "Route created.");

        return savedRoute;
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + id));
    }

    public List<Route> getRoutesFromAirport(String originCode) {
        Airport origin = getAirport(originCode);
        return routeRepository.findByOriginAirport(origin);
    }

    public List<Route> searchRoutes(String originCode, String destinationCode) {
        if (originCode != null && destinationCode != null) {
            Airport origin = getAirport(originCode);
            Airport destination = getAirport(destinationCode);
            return routeRepository.findByOriginAirportAndDestinationAirport(origin, destination);
        }

        if (originCode != null) {
            Airport origin = getAirport(originCode);
            return routeRepository.findByOriginAirport(origin);
        }

        if (destinationCode != null) {
            Airport destination = getAirport(destinationCode);
            return routeRepository.findByDestinationAirport(destination);
        }

        return routeRepository.findAll();
    }

    public Route updateRoute(Long id,
                             Integer estimatedFlightTimeMinutes,
                             Integer minimumRange,
                             Integer minimumCapacity) {

        Route route = getRouteById(id);

        if (route.getStatus() == RouteStatus.DEACTIVATED) {
            throw new IllegalArgumentException("Cannot update a deactivated route.");
        }

        if (estimatedFlightTimeMinutes != null && estimatedFlightTimeMinutes <= 0) {
            throw new IllegalArgumentException("Estimated flight time must be greater than zero.");
        }

        if (minimumRange != null && minimumRange <= 0) {
            throw new IllegalArgumentException("Minimum range must be greater than zero.");
        }

        if (minimumCapacity != null && minimumCapacity <= 0) {
            throw new IllegalArgumentException("Minimum capacity must be greater than zero.");
        }

        if (estimatedFlightTimeMinutes != null) {
            route.setEstimatedFlightTimeMinutes(estimatedFlightTimeMinutes);
        }

        if (minimumRange != null) {
            route.setMinimumRange(minimumRange);
        }

        if (minimumCapacity != null) {
            route.setMinimumCapacity(minimumCapacity);
        }

        Route updatedRoute = routeRepository.save(route);
        saveHistory(updatedRoute, "UPDATED", "Route updated.");

        return updatedRoute;
    }

    public Route deactivateRoute(Long id) {
        Route route = getRouteById(id);

        if (route.getStatus() == RouteStatus.DEACTIVATED) {
            throw new IllegalArgumentException("Route is already deactivated.");
        }

        route.setStatus(RouteStatus.DEACTIVATED);

        Route updatedRoute = routeRepository.save(route);
        saveHistory(updatedRoute, "DEACTIVATED", "Route deactivated.");

        return updatedRoute;
    }

    public List<RouteHistory> getRouteHistory(Long routeId) {
        getRouteById(routeId);
        return routeHistoryRepository.findByRouteId(routeId);
    }

    private Airport getAirport(String iataCode) {
        // CORREÇÃO 2: Embrulhar a String num "new IataCode()" antes de procurar na base de dados
        return airportRepository.findById(new IataCode(iataCode))
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with IATA code: " + iataCode));
    }

    private void validateRouteInput(String originCode,
                                    String destinationCode,
                                    Integer estimatedFlightTimeMinutes,
                                    Integer minimumRange,
                                    Integer minimumCapacity) {

        if (originCode == null || !originCode.matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Origin airport must be a valid 3-letter IATA code.");
        }

        if (destinationCode == null || !destinationCode.matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException("Destination airport must be a valid 3-letter IATA code.");
        }

        if (estimatedFlightTimeMinutes == null || estimatedFlightTimeMinutes <= 0) {
            throw new IllegalArgumentException("Estimated flight time must be greater than zero.");
        }

        if (minimumRange == null || minimumRange <= 0) {
            throw new IllegalArgumentException("Minimum range must be greater than zero.");
        }

        if (minimumCapacity == null || minimumCapacity <= 0) {
            throw new IllegalArgumentException("Minimum capacity must be greater than zero.");
        }
    }

    private void saveHistory(Route route, String action, String description) {
        RouteHistory history = new RouteHistory();
        history.setRoute(route);
        history.setAction(action);
        history.setDescription(description);
        routeHistoryRepository.save(history);
    }
}