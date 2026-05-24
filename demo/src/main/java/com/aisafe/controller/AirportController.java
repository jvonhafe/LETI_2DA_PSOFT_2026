package com.aisafe.controller;

import com.aisafe.application.airport.RegisterAirportUseCase;
import com.aisafe.application.airport.UpdateAirportStatusUseCase;
import com.aisafe.core.exception.ResourceNotFoundException;
import com.aisafe.model.Airport;
import com.aisafe.model.IataCode;
import com.aisafe.repository.AirportRepository;
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
    private RegisterAirportUseCase registerAirportUseCase;

    @Autowired
    private UpdateAirportStatusUseCase updateAirportStatusUseCase;


    @Autowired
    private AirportRepository airportRepository;

    // US106: Registar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Airport createAirport(@RequestBody Airport airport) {
        return registerAirportUseCase.execute(airport);
    }

    // US107: Obter por ID
    @GetMapping("/{iata}")
    public EntityModel<Airport> getAirportById(@PathVariable String iata) {
        IataCode searchId = new IataCode(iata);

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

    // US109: Atualizar Estado (Lógica movida para o UpdateAirportStatusUseCase)
    @PatchMapping("/{iata}/status")
    public Airport updateAirportStatus(@PathVariable String iata, @RequestParam String status) {
        return updateAirportStatusUseCase.execute(iata, status);
    }
}