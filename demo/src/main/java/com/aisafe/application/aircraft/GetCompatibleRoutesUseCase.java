package com.aisafe.application.aircraft;

import com.aisafe.core.exception.ResourceNotFoundException;
import com.aisafe.model.Aircraft;
import com.aisafe.model.AircraftModel;
import com.aisafe.model.AircraftRegistration;
import com.aisafe.model.Route;
import com.aisafe.repository.AircraftModelRepository;
import com.aisafe.repository.AircraftRepository;
import com.aisafe.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCompatibleRoutesUseCase {

    private final AircraftRepository aircraftRepository;
    private final AircraftModelRepository aircraftModelRepository;
    private final RouteRepository routeRepository;

    public GetCompatibleRoutesUseCase(AircraftRepository aircraftRepository,
                                      AircraftModelRepository aircraftModelRepository,
                                      RouteRepository routeRepository) {
        this.aircraftRepository = aircraftRepository;
        this.aircraftModelRepository = aircraftModelRepository;
        this.routeRepository = routeRepository;
    }

    public List<Route> execute(String registrationNumber) {
        AircraftRegistration registrationVO = new AircraftRegistration(registrationNumber);

        Aircraft aircraft = aircraftRepository.findById(registrationVO)
                .orElseThrow(() -> new ResourceNotFoundException("Aeronave não encontrada com a matrícula: " + registrationNumber));

        AircraftModel model = aircraftModelRepository.findById(aircraft.getModelId())
                .orElseThrow(() -> new ResourceNotFoundException("Modelo de aeronave não encontrado!"));

        List<Route> allRoutes = routeRepository.findAll();

        return allRoutes.stream()
                .filter(route -> route.getStatus() != null && "ACTIVE".equals(route.getStatus().toString()))
                .filter(route -> route.getMinimumRange() <= model.getMaxRange())
                .filter(route -> route.getMinimumCapacity() <= model.getMaxCapacity().getValue())
                .collect(Collectors.toList());
    }
}