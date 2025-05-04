package com.conexa.technicalchallenge.controller.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class VehicleDTO {
    private String id;
    private String uid;
    public String model;
    public String vehicleClass;
    public String manufacturer;
    public String costInCredits;
    public String length;
    public String crew;
    public String passengers;
    public String maxAtmospheringSpeed;
    public String cargoCapacity;
    public String consumables;
    public List<String> films;
    public List<String> pilots;
    public String created;
    public String edited;
    public String name;
    public String url;
}
