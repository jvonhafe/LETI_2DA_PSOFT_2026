package com.aisafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
public class AircraftModel {

    @Id
    @NotBlank(message = "O ID do modelo é obrigatorio.")
    private String modelId;

    @NotBlank(message = "O nome do modelo é obrigatorio.")
    private String modelName;

    @NotBlank(message = "O fabricante é obrigatorio.")
    private String manufacturer;

    @Positive(message = "A capacidade de lugares deve ser um valor positivo.")
    private int maxCapacity;

    @Positive(message = "A capacidade de combustivel deve ser um valor positivo.")
    private double fuelCapacity;

    @Positive(message = "O alcance maximo deve ser um valor positivo.")
    private double maxRange;

    @Positive(message = "A velocidade de cruzeiro deve ser um valor positivo.")
    private double cruisingSpeed;
}