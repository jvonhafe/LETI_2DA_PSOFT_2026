package com.aisafe.controller;

import com.aisafe.core.exception.ResourceNotFoundException;
import com.aisafe.model.Airport;
import com.aisafe.model.IataCode;
import com.aisafe.repository.AirportRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/airports")
@Tag(name = "Airports", description = "Gestão de Aeroportos")
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    // US106: Registar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Airport createAirport(@RequestBody Airport airport) {
        if (airportRepository.existsById(airport.getIataCode())) {
            throw new IllegalArgumentException("Já existe um aeroporto com IATA: " + airport.getIataCode().getCode());
        }
        return airportRepository.save(airport);
    }

    // US107: Obter por ID
    @GetMapping("/{iata}")
    public EntityModel<Airport> getAirportById(@PathVariable String iata) {
        IataCode searchId = new IataCode(iata); // <-- Transforma a String no Value Object

        Airport airport = airportRepository.findById(searchId)
                .orElseThrow(() -> new ResourceNotFoundException("Aeroporto não encontrado: " + iata));

        return EntityModel.of(airport,
                linkTo(methodOn(AirportController.class).getAirportById(airport.getIataCode().getCode())).withSelfRel(),
                linkTo(methodOn(AirportController.class).getAllAirports()).withRel("todos-aeroportos")
        );
    }

    // US107: Listar todos
    @GetMapping
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    // US108: Pesquisar por cidade
    @GetMapping("/search")
    public List<Airport> searchAirports(@RequestParam String city) {
        return airportRepository.findByCityContainingIgnoreCase(city);
    }

    // US109: Atualizar Estado
    @PatchMapping("/{iata}/status")
    public Airport updateAirportStatus(@PathVariable String iata, @RequestParam String status) {
        IataCode searchId = new IataCode(iata); // <-- Transforma a String no Value Object

        Airport airport = airportRepository.findById(searchId)
                .orElseThrow(() -> new ResourceNotFoundException("Aeroporto não encontrado: " + iata));

        airport.updateStatus(status); // <-- Usa o método seguro da tua Entidade!
        return airportRepository.save(airport);
    }
}