package com.conexa.technicalchallenge.repository.dao.starship;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StarshipProperties {
    private String created;
    private String edited;
    private String consumables;
    private String name;
    @JsonProperty("cargo_capacity")
    private String cargoCapacity;
    private String passengers;
    @JsonProperty("max_atmosphering_speed")
    private String maxAtmospheringSpeed;
    private String crew;
    private String length;
    private String model;
    @JsonProperty("cost_in_credits")
    private String costInCredits;
    private String manufacturer;
    private List<String> pilots;
    private String MGLT;
    @JsonProperty("starship_class")
    private String starshipClass;
    @JsonProperty("hyperdrive_rating")
    private String hyperdriveRating;
    private List<String> films;
    private String url;

}

