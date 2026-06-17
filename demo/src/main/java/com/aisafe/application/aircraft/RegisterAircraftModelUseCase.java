package com.aisafe.application.aircraft;

import com.aisafe.model.AircraftModel;
import com.aisafe.model.Capacity;
import com.aisafe.repository.AircraftModelRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterAircraftModelUseCase {

    private final AircraftModelRepository repository;

    public RegisterAircraftModelUseCase(AircraftModelRepository repository) {
        this.repository = repository;
    }

    public AircraftModel execute(String modelId, String modelName, String manufacturer, int maxCapacity,
                                 double fuelCapacity, double maxRange, double cruisingSpeed) {

        AircraftModel model = new AircraftModel();
        model.setModelId(modelId);
        model.setModelName(modelName);
        model.setManufacturer(manufacturer);
        model.setMaxCapacity(new Capacity(maxCapacity));
        model.setFuelCapacity(fuelCapacity);
        model.setMaxRange(maxRange);
        model.setCruisingSpeed(cruisingSpeed);

        return repository.save(model);
    }
}