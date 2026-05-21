package com.aisafe.controller;

import com.aisafe.model.Aircraft;
import com.aisafe.repository.AircraftRepository;
import com.aisafe.repository.AircraftModelRepository;
import com.aisafe.core.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AircraftModelRepository aircraftModelRepository;

    @PostMapping
    public Aircraft registerAircraft(@Valid @RequestBody Aircraft aircraft) {
        if (!aircraftModelRepository.existsById(aircraft.getModelId())) {
            throw new ResourceNotFoundException("Não é possível registar a aeronave. O modelo '" + aircraft.getModelId() + "' não existe.");
        }
        return aircraftRepository.save(aircraft);
    }

    @GetMapping("/{registration}")
    public Aircraft getAircraftDetails(@PathVariable String registration) {
        return aircraftRepository.findById(registration)
                .orElseThrow(() -> new ResourceNotFoundException("Aeronave com a matrícula " + registration + " não encontrada."));
    }

    @GetMapping("/search")
    public List<Aircraft> searchAircrafts(@RequestParam(required = false) String modelId,
                                          @RequestParam(required = false) String status,
                                          @RequestParam(required = false) Integer year) {
        return aircraftRepository.searchAircrafts(modelId, status, year);
    }

    @PatchMapping("/{registration}/status")
    public Aircraft updateStatus(@PathVariable String registration, @RequestParam String newStatus) {
        String statusUpper = newStatus.toUpperCase();

        if (!statusUpper.matches("^(ACTIVE|INACTIVE|UNDER_MAINTENANCE)$")) {
            throw new IllegalArgumentException("Estado inválido. Escolha entre: ACTIVE, INACTIVE ou UNDER_MAINTENANCE");
        }

        Aircraft aircraft = aircraftRepository.findById(registration)
                .orElseThrow(() -> new ResourceNotFoundException("Aeronave com a matrícula " + registration + " não encontrada."));

        aircraft.setStatus(statusUpper);
        return aircraftRepository.save(aircraft);
    }
}