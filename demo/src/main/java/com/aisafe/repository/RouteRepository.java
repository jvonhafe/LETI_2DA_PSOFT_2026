package com.aisafe.repository;

import com.aisafe.model.Airport;
import com.aisafe.model.Route;
import com.aisafe.model.RouteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByOriginAirport(Airport originAirport);

    List<Route> findByDestinationAirport(Airport destinationAirport);

    List<Route> findByOriginAirportAndDestinationAirport(Airport originAirport, Airport destinationAirport);

    boolean existsByOriginAirportAndDestinationAirportAndStatus(
            Airport originAirport,
            Airport destinationAirport,
            RouteStatus status
    );
}