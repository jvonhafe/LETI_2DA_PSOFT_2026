package com.aisafe.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AircraftModel {

    @Id
    private String modelId;
    private String modelName;
    private String manufacturer;

    @Embedded
    private Capacity maxCapacity;

    private double fuelCapacity;
    private double maxRange;
    private double cruisingSpeed;
}