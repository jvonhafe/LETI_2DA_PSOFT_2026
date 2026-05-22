package com.aisafe.controller;

import com.aisafe.core.exception.ResourceNotFoundException;
import com.aisafe.model.Airport;
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
@Tag(name = "Airports", description = "Gestão de Aeroportos (US106, US107, US108)")
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    // ==========================================
    // US106: Registar um novo aeroporto
    // ==========================================
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registar Aeroporto", description = "Cria um novo aeroporto no sistema (US106).")
    public Airport createAirport(@RequestBody Airport airport) {

        if (airportRepository.existsById(airport.getIataCode())) {
            throw new IllegalArgumentException("Já existe um aeroporto com o código IATA: " + airport.getIataCode());
        }

        return airportRepository.save(airport);
    }

    // ==========================================
    // US107: Ver Detalhes de um Aeroporto
    // ==========================================
    @GetMapping("/{iata}")
    @Operation(summary = "Obter Aeroporto", description = "Devolve os detalhes de um aeroporto com links HATEOAS (US107).")
    public EntityModel<Airport> getAirportById(@PathVariable String iata) {

        Airport airport = airportRepository.findById(iata.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Aeroporto não encontrado com o código IATA: " + iata));

        // hateoas
        return EntityModel.of(airport,
                linkTo(methodOn(AirportController.class).getAirportById(airport.getIataCode())).withSelfRel(),
                linkTo(methodOn(AirportController.class).getAllAirports()).withRel("todos-aeroportos")
        );
    }

    // ==========================================
    // US107: Ver todos os Aeroportos
    // ==========================================
    @GetMapping
    @Operation(summary = "Listar Aeroportos", description = "Devolve a lista simples de todos os aeroportos (US107).")
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    // ==========================================
    // US108: Pesquisar por Cidade (LISTA LIMPA)
    // ==========================================
    @GetMapping("/search")
    @Operation(summary = "Pesquisar Aeroportos", description = "Pesquisa aeroportos filtrando pelo nome da cidade (US108).")
    public List<Airport> searchAirports(@RequestParam String city) {
        return airportRepository.findByCityContainingIgnoreCase(city);
    }
}