package com.conexa.technicalchallenge.repository.dao.vehicle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDAO {
    @JsonProperty("uid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    public String model;
    @JsonProperty("vehicle_class")
    public String vehicleClass;
    public String manufacturer;
    @JsonProperty("cost_in_credits")
    public String costInCredits;
    public String length;
    public String crew;
    public String passengers;
    @JsonProperty("max_atmosphering_speed")
    public String maxAtmospheringSpeed;
    @JsonProperty("cargo_capacity")
    public String cargoCapacity;
    public String consumables;
    public List<String> films;
    public List<String> pilots;
    public String created;
    public String edited;
    public String name;
    public String url;

}
