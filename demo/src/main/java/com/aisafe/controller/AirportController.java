package com.aisafe.controller;

import com.aisafe.application.airport.RegisterAirportUseCase;
import com.aisafe.application.airport.RegisterDetailedAirportUseCase;
import com.aisafe.application.airport.UpdateAirportDetailsUseCase;
import com.aisafe.application.airport.UpdateAirportStatusUseCase;
import com.aisafe.core.exception.ResourceNotFoundException;
import com.aisafe.model.Airport;
import com.aisafe.model.BusiestAirportDto; // NOVO US210
import com.aisafe.model.IataCode;
import com.aisafe.repository.AirportRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest; // NOVO US210
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private RegisterDetailedAirportUseCase registerDetailedAirportUseCase;

    @Autowired
    private UpdateAirportDetailsUseCase updateAirportDetailsUseCase;

    @Autowired
    private AirportRepository airportRepository;

    // US106: Registar (Simples)
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Airport createAirport(@RequestBody Airport airport) {
        return registerAirportUseCase.execute(airport);
    }

    // US207: Registar Aeroporto Detalhado (Com Fotos e Instalações)
    @PreAuthorize("hasRole('BACKOFFICE')")
    @PostMapping("/detailed")
    @ResponseStatus(HttpStatus.CREATED)
    public Airport createDetailedAirport(@RequestBody RegisterDetailedAirportUseCase.DetailedAirportDto request) {
        return registerDetailedAirportUseCase.execute(request);
    }

    // US107: Obter por ID
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE', 'ATCC')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE', 'ATCC')")
    @GetMapping
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    // US108: Pesquisar por cidade
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE', 'ATCC')")
    @GetMapping("/search")
    public List<Airport> searchAirports(@RequestParam String city) {
        return airportRepository.findByCityContainingIgnoreCase(city);
    }

    // US109: Atualizar Estado
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE')")
    @PatchMapping("/{iata}/status")
    public Airport updateAirportStatus(@PathVariable String iata, @RequestParam String status) {
        return updateAirportStatusUseCase.execute(iata, status);
    }

    // US208: Atualizar Detalhes do Aeroporto (Contactos e Horários)
    @PreAuthorize("hasRole('BACKOFFICE')")
    @PatchMapping("/{iata}/details")
    public Airport updateAirportDetails(
            @PathVariable String iata,
            @RequestBody UpdateAirportDetailsUseCase.UpdateDetailsRequest request) {

        return updateAirportDetailsUseCase.execute(iata, request);
    }

    // --- NOVO: US210: Estatísticas dos Aeroportos mais movimentados ---
    @PreAuthorize("hasRole('BACKOFFICE')") // Apenas o Backoffice gera estatísticas
    @GetMapping("/statistics/busiest")
    public List<BusiestAirportDto> getBusiestAirports(
            @RequestParam(defaultValue = "5") int limit) {
        // CQRS: O Controller pede diretamente ao Repository e usa o PageRequest para aplicar o Limite
        return airportRepository.findBusiestAirports(PageRequest.of(0, limit));
    }

    // --- NOVO: US211: Ver aeroportos agrupados por país ou região ---
    @PreAuthorize("hasAnyRole('ADMIN', 'BACKOFFICE', 'ATCC')") // ATCC pode ver!
    @GetMapping("/grouped")
    public Map<String, List<Airport>> getAirportsGrouped(@RequestParam(defaultValue = "country") String by) {

        List<Airport> allAirports = airportRepository.findAll();

        if ("country".equalsIgnoreCase(by)) {
            // CQRS com Java Streams: Agrupa a lista de aeroportos usando a chave "Country"
            return allAirports.stream().collect(Collectors.groupingBy(Airport::getCountry));
        } else if ("timezone".equalsIgnoreCase(by)) {
            // Agrupa por fuso horário (bónus)
            return allAirports.stream().collect(Collectors.groupingBy(Airport::getTimezone));
        } else {
            throw new IllegalArgumentException("Parâmetro de agrupamento não suportado. Use 'country' ou 'timezone'.");
        }
    }
}