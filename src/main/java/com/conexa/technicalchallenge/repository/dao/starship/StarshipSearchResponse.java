package com.conexa.technicalchallenge.repository.dao.starship;

import com.conexa.technicalchallenge.repository.dao.people.PersonResult;
import lombok.Data;

import java.util.List;

@Data
public class StarshipSearchResponse {
    private String message;
    private List<StarshipResult> result;
}
