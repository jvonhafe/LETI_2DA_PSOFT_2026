package com.aisafe.application.route;

import com.aisafe.model.Route;
import com.aisafe.model.RouteStatus;
import com.aisafe.repository.RouteRepository;
import com.aisafe.repository.ScheduledFlightRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetActiveRoutesSortedUseCase {
    private final RouteRepository routeRepository;
    private final ScheduledFlightRepository flightRepository;

    public GetActiveRoutesSortedUseCase(RouteRepository routeRepository, ScheduledFlightRepository flightRepository) {
        this.routeRepository = routeRepository;
        this.flightRepository = flightRepository;
    }

    public List<Route> execute(String sortBy) {
        List<Route> activeRoutes = routeRepository.findAll().stream()
                .filter(r -> r.getStatus() == RouteStatus.ACTIVE)
                .collect(Collectors.toList());

        if ("distance".equalsIgnoreCase(sortBy)) {
            activeRoutes.sort(Comparator.comparing(r -> r.getDistanceKm() == null ? 0.0 : r.getDistanceKm()));
        } else if ("popularity".equalsIgnoreCase(sortBy)) {
            activeRoutes.sort((r1, r2) -> {
                long count1 = flightRepository.findByRouteId(r1.getId()).size();
                long count2 = flightRepository.findByRouteId(r2.getId()).size();
                return Long.compare(count2, count1); // Decrescente
            });
        }
        return activeRoutes;
    }
}