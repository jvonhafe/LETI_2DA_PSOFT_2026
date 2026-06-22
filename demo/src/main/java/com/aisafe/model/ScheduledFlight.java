package com.aisafe.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ScheduledFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Corresponde ao FlightId do teu diagrama UML

    @Column(nullable = false)
    private Long routeId; // Ligação por ID à Rota

    @Column(nullable = false)
    private String aircraftRegistration; // Ligação por ID à Aeronave

    @Embedded
    private FlightSchedule schedule;

    public ScheduledFlight(Long routeId, String aircraftRegistration, FlightSchedule schedule) {
        this.routeId = routeId;
        this.aircraftRegistration = aircraftRegistration;
        this.schedule = schedule;
    }
}