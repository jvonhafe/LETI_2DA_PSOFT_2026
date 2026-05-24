package com.aisafe.controller;

import com.aisafe.application.RegisterAircraftUseCase;
import com.aisafe.core.exception.AircraftNotFoundException;
import com.aisafe.model.Aircraft;
import com.aisafe.model.AircraftRegistration;
import com.aisafe.repository.AircraftRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    private final RegisterAircraftUseCase registerUseCase;
    private final AircraftRepository aircraftRepository;

    public AircraftController(RegisterAircraftUseCase registerUseCase, AircraftRepository aircraftRepository) {
        this.registerUseCase = registerUseCase;
        this.aircraftRepository = aircraftRepository;
    }

    @PostMapping
    public Aircraft registerAircraft(@RequestBody AircraftDTO dto) {
        return registerUseCase.execute(dto.registrationNumber, dto.modelId, dto.manufacturingDate, dto.status);
    }

    @GetMapping("/{registration}")
    public Aircraft getAircraftDetails(@PathVariable String registration) {
        return aircraftRepository.findById(new AircraftRegistration(registration))
                .orElseThrow(() -> new AircraftNotFoundException(registration)); // Exceção customizada aqui!
    }

    @GetMapping("/search")
    public List<Aircraft> searchAircrafts(@RequestParam(required = false) String modelId,
                                          @RequestParam(required = false) String status,
                                          @RequestParam(required = false) Integer year) {
        return aircraftRepository.searchAircrafts(modelId, status, year);
    }

    @Valid @PatchMapping("/{registration}/status")
    public Aircraft updateStatus(@PathVariable String registration, @RequestParam String newStatus) {
        Aircraft aircraft = aircraftRepository.findById(new AircraftRegistration(registration))
                .orElseThrow(() -> new AircraftNotFoundException(registration));

        aircraft.setStatus(newStatus.toUpperCase());
        return aircraftRepository.save(aircraft);
    }

    public static class AircraftDTO {
        public String registrationNumber;
        public String modelId;
        public LocalDate manufacturingDate;
        public String status;
    }
}