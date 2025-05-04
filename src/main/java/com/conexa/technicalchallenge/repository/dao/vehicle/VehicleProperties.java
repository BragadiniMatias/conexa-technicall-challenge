package com.conexa.technicalchallenge.repository.dao.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VehicleProperties {
    public String created;
    public String edited;
    public String consumables;
    public String name;
    @JsonProperty("cargo_capacity")
    public String cargoCapacity;
    public String passengers;
    @JsonProperty("max_atmosphering_speed")
    private String maxAtmospheringSpeed;
    public String crew;
    public String length;
    public String model;
    @JsonProperty("cost_in_credits")
    private String costInCredits;
    public String manufacturer;
    @JsonProperty("vehicle_class")
    public String vehicleClass;
    public List<String> pilots;
    public List<String> films;
    public String url;

}

