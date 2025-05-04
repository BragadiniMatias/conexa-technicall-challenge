package com.conexa.technicalchallenge.repository.dao.starship;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StarshipResult {
    private StarshipProperties properties;
    private String description;
    private String uid;
    @JsonProperty("_id")
    private String id;
    @JsonProperty("__v")
    private int version;
}
