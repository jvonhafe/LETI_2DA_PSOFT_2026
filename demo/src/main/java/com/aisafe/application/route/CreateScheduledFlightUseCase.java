package com.aisafe.application.route;

import com.aisafe.model.*;
import com.aisafe.repository.*;
import com.aisafe.core.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreateScheduledFlightUseCase {

    private final ScheduledFlightRepository scheduledFlightRepository;
    private final RouteRepository routeRepository;
    private final AircraftRepository aircraftRepository;
    private final AircraftModelRepository aircraftModelRepository;

    public CreateScheduledFlightUseCase(
            ScheduledFlightRepository scheduledFlightRepository,
            RouteRepository routeRepository,
            AircraftRepository aircraftRepository,
            AircraftModelRepository aircraftModelRepository) {
        this.scheduledFlightRepository = scheduledFlightRepository;
        this.routeRepository = routeRepository;
        this.aircraftRepository = aircraftRepository;
        this.aircraftModelRepository = aircraftModelRepository;
    }

    @Transactional
    public ScheduledFlight execute(Long routeId, String aircraftRegistrationStr, LocalDateTime departureTime) {

        // 1. Validar se a Rota existe
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Rota com ID " + routeId + " não encontrada."));

        if (route.getStatus() == RouteStatus.DEACTIVATED) {
            throw new IllegalArgumentException("Não é possível agendar voos para uma rota desativada.");
        }

        // 2. Obter Aeroportos da Rota e validar se estão operacionais
        Airport origin = route.getOriginAirport();
        Airport destination = route.getDestinationAirport();

        if (!"OPERATIONAL".equalsIgnoreCase(origin.getStatus().toString()) ||
                !"OPERATIONAL".equalsIgnoreCase(destination.getStatus().toString())) {
            throw new IllegalArgumentException("Ambos os aeroportos devem estar operacionais.");
        }

        // 3. Converter String para Value Object e procurar Aeronave
        AircraftRegistration registrationVO = new AircraftRegistration(aircraftRegistrationStr);

        Aircraft aircraft = aircraftRepository.findById(registrationVO)
                .orElseThrow(() -> new ResourceNotFoundException("Aeronave " + aircraftRegistrationStr + " não encontrada."));

        if (!"ACTIVE".equalsIgnoreCase(aircraft.getStatus().toString())) {
            throw new IllegalArgumentException("A aeronave selecionada não está disponível (Status: " + aircraft.getStatus() + ").");
        }

        // 4. Validar se a Aeronave tem o modelo registado para ver especificações
        AircraftModel model = aircraftModelRepository.findById(aircraft.getModelId())
                .orElseThrow(() -> new ResourceNotFoundException("Modelo da aeronave não encontrado no catálogo."));

        // 5. Validar Requisitos de Alcance (Range) e Capacidade (Seats)
        if (model.getMaxRange() < route.getMinimumRange()) {
            throw new IllegalArgumentException("A aeronave não tem alcance suficiente para esta rota.");
        }
        int lugaresDoAviao = model.getMaxCapacity().getValue();
        if (lugaresDoAviao < route.getMinimumCapacity()) {
            throw new IllegalArgumentException("A aeronave não tem capacidade de passageiros suficiente para esta rota.");
        }

        // 6. Calcular hora de chegada
        LocalDateTime arrivalTime = departureTime.plusMinutes(route.getEstimatedFlightTimeMinutes());
        FlightSchedule schedule = new FlightSchedule(departureTime, arrivalTime);

        // 7. Criar e Persistir o Voo Agendado
        ScheduledFlight flight = new ScheduledFlight(routeId, aircraftRegistrationStr, schedule);
        return scheduledFlightRepository.save(flight);
    }}