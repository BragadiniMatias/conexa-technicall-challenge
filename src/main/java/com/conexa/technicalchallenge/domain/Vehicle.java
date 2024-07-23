package com.conexa.technicalchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private String id;
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
