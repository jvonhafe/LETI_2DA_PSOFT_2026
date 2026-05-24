package com.aisafe.repository;

import com.aisafe.model.Airport;
import com.aisafe.model.IataCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, IataCode> {
    List<Airport> findByCityContainingIgnoreCase(String city);
}