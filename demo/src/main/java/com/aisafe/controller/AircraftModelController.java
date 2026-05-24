package com.aisafe.controller;

import com.aisafe.application.aircraft.RegisterAircraftModelUseCase;
import com.aisafe.model.AircraftModel;
import com.aisafe.repository.AircraftModelRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/aircraft-models")
public class AircraftModelController {

    private final RegisterAircraftModelUseCase registerUseCase;
    private final AircraftModelRepository modelRepository;

    public AircraftModelController(RegisterAircraftModelUseCase registerUseCase, AircraftModelRepository modelRepository) {
        this.registerUseCase = registerUseCase;
        this.modelRepository = modelRepository;
    }

    @PostMapping
    public AircraftModel registerModel(@RequestBody AircraftModelDTO dto) {
        return registerUseCase.execute(dto.modelId, dto.modelName, dto.manufacturer,
                dto.maxCapacity, dto.fuelCapacity, dto.maxRange, dto.cruisingSpeed);
    }

    @GetMapping
    public List<AircraftModel> getAllModels() {
        return modelRepository.findAll();
    }

    public static class AircraftModelDTO {
        public String modelId;
        public String modelName;
        public String manufacturer;
        public int maxCapacity;
        public double fuelCapacity;
        public double maxRange;
        public double cruisingSpeed;
    }
}