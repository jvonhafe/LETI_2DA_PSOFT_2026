package com.aisafe.controller;

import com.aisafe.model.Airport;
import com.aisafe.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    // US107 - Ver todos os aeroportos (para os teus colegas testarem)
    @GetMapping
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    // US107 - Ver detalhes por IATA Code
    @GetMapping("/{iata}")
    public Airport getAirportById(@PathVariable String iata) {
        return airportRepository.findById(iata).orElse(null);
    }
}