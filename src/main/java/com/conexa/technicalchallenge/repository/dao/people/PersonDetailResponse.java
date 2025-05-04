package com.conexa.technicalchallenge.repository.dao.people;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PersonDetailResponse {
    private String message;
    private PersonResult result;
}
