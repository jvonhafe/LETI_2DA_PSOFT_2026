package com.aisafe.model;

public class AircraftModelUtilizationDTO {
    private String modelId;
    private String modelName;
    private String manufacturer;
    private double totalFlightHours;

    public AircraftModelUtilizationDTO(String modelId, String modelName, String manufacturer, double totalFlightHours) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.totalFlightHours = totalFlightHours;
    }

    public String getModelId() { return modelId; }
    public String getModelName() { return modelName; }
    public String getManufacturer() { return manufacturer; }
    public double getTotalFlightHours() { return totalFlightHours; }
}