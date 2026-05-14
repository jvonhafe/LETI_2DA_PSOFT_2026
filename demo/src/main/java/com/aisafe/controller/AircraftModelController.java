package com.aisafe.controller;

import com.aisafe.model.AircraftModel;
import com.aisafe.repository.AircraftModelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/aircraft-models")
public class AircraftModelController {

    @Autowired
    private AircraftModelRepository modelRepository;

    @PostMapping
    public AircraftModel registerModel(@Valid @RequestBody AircraftModel model) {
        return modelRepository.save(model);
    }

    @GetMapping
    public List<AircraftModel> getAllModels() {
        return modelRepository.findAll();
    }
}