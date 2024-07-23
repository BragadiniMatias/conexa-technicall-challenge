package com.conexa.technicalchallenge.repository.dao.starship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarshipDAO {
    @JsonProperty("uid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    private String name;
    private String model;
    @JsonProperty("starship_class")
    private String starshipClass;
    private String manufacturer;
    @JsonProperty("cost_in_credits")
    private String costInCredits;
    private String length;
    private String crew;
    private String passengers;
    @JsonProperty("max_atmosphering_speed")
    private String maxAtmospheringSpeed;
    @JsonProperty("hyperdrive_rating")
    private String hyperdriveRating;
    @JsonProperty("MGLT")
    private String mglt;
    @JsonProperty("cargo_capacity")
    private String cargoCapacity;
    private String consumables;
    private List<String> films;
    private List<String> pilots;
    private String url;
    private String created;
    private String edited;

}
