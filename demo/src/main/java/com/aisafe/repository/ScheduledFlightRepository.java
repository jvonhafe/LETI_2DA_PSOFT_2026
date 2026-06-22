package com.aisafe.repository;

import com.aisafe.model.ScheduledFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledFlightRepository extends JpaRepository<ScheduledFlight, Long> {
    List<ScheduledFlight> findByAircraftRegistration(String aircraftRegistration);
    List<ScheduledFlight> findByRouteId(Long routeId);
}