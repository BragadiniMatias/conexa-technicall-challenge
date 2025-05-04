package com.conexa.technicalchallenge.controller.dto.people;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PeopleDTO {
    private String uid;
    private String id;
    private String height;
    private String mass;
    private String hairColor;
    private String skinColor;
    private String eyeColor;
    private String birthYear;
    private String gender;
    private String created;
    private String edited;
    private String name;
    private String homeworld;
    private String url;
}
