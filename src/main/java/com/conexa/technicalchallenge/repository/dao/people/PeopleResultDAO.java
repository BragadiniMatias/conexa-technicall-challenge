package com.conexa.technicalchallenge.repository.dao.people;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleResultDAO {
    private String description;
    @JsonProperty("_id")
    private String id;
    private String uid;
    @JsonProperty("__v")
    private int version;
    @JsonProperty("properties")
    private PeopleDAO properties;

}
