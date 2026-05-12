package com.aisafe.repository;

import com.aisafe.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {
    // Aqui o Spring faz a magia de ligar à Base de Dados sozinho
}