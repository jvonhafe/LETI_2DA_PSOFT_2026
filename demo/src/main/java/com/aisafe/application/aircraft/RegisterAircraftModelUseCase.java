package com.aisafe.application.aircraft;

import com.aisafe.model.AircraftModel;
import com.aisafe.model.Capacity;
import com.aisafe.repository.AircraftModelRepository;
import org.springframework.stereotype.Service;
import com.aisafe.model.MediaImage;

@Service
public class RegisterAircraftModelUseCase {

    private final AircraftModelRepository repository;

    public RegisterAircraftModelUseCase(AircraftModelRepository repository) {
        this.repository = repository;
    }

    public AircraftModel execute(String modelId, String modelName, String manufacturer, int maxCapacity,
                                 double fuelCapacity, double maxRange, double cruisingSpeed,
                                 String imageUrl, String imageDescription) {

        AircraftModel model = new AircraftModel();
        model.setModelId(modelId);
        model.setModelName(modelName);
        model.setManufacturer(manufacturer);
        model.setMaxCapacity(new Capacity(maxCapacity));
        model.setFuelCapacity(fuelCapacity);
        model.setMaxRange(maxRange);
        model.setCruisingSpeed(cruisingSpeed);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            MediaImage image = new MediaImage();
            image.setImageUrl(imageUrl);
            image.setDescription(imageDescription);
            model.setImage(image);
        }

        return repository.save(model);
    }
}