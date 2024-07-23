package com.conexa.technicalchallenge.repository.dao.starship;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarshipResultDAO {
    private String description;
    @JsonProperty("_id")
    private String id;
    private String uid;
    @JsonProperty("__v")
    private int version;
    @JsonProperty("properties")
    private StarshipDAO properties;

}
