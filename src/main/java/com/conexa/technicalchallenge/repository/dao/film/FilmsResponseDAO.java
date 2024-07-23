package com.conexa.technicalchallenge.repository.dao.film;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmsResponseDAO {

    @JsonProperty("message")
    private String message;
    @JsonProperty("result")
    private List<FilmResultDAO> result;

}
