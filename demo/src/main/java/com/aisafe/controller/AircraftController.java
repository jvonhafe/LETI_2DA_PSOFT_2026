package com.aisafe.controller;

import com.aisafe.model.Aircraft;
import com.aisafe.repository.AircraftRepository;
import com.aisafe.core.exception.ResourceNotFoundException; // 👈 O novo import limpo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;

    @PostMapping
    public Aircraft registerAircraft(@RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    //us103
    @GetMapping("/{registration}")
    public Aircraft getAircraftDetails(@PathVariable String registration) {
        return aircraftRepository.findById(registration)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft não encontrada"));
    }

    @GetMapping("/search")
    public List<Aircraft> searchAircrafts(@RequestParam(required = false) String modelId,
                                          @RequestParam(required = false) String status,
                                          @RequestParam(required = false) Integer year) {
        return aircraftRepository.searchAircrafts(modelId, status, year);
    }

    //us105
    @PatchMapping("/{registration}/status")
    public Aircraft updateStatus(@PathVariable String registration, @RequestParam String newStatus) {
        Aircraft aircraft = aircraftRepository.findById(registration)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft não encontrada"));

        aircraft.setStatus(newStatus);
        return aircraftRepository.save(aircraft);
    }
}