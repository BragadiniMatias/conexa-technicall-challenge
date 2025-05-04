package com.conexa.technicalchallenge.repository.dao.people;

import com.conexa.technicalchallenge.repository.dao.people.PersonProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PersonResult {
    private PersonProperties properties;
    private String description;
    private String uid;
    @JsonProperty("_id")
    private String id;
    @JsonProperty("__v")
    private int version;
}
