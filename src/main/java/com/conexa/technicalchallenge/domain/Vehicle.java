package com.conexa.technicalchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {
    private String uid;
    private String id;
    private String model;
    private String vehicleClass;
    private String manufacturer;
    private String costInCredits;
    private String length;
    private String crew;
    private String passengers;
    private String maxAtmospheringSpeed;
    private String cargoCapacity;
    private String consumables;
    private List<String> films;
    private List<String> pilots;
    private String created;
    private String edited;
    private String name;
    private String url;
}
