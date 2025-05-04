package com.conexa.technicalchallenge.repository.dao.people;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PersonProperties {
    private String name;
    private String gender;
    @JsonProperty("skin_color")
    private String skinColor;
    @JsonProperty("hair_color")
    private String hairColor;
    private String height;
    @JsonProperty("eye_color")
    private String eyeColor;
    private String mass;
    private String homeworld;
    @JsonProperty("birth_year")
    private String birthYear;
    private String url;
    private String created;
    private String edited;
}
