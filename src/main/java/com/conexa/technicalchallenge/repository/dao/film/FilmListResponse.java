package com.conexa.technicalchallenge.repository.dao.film;

import lombok.Data;

import java.util.List;

@Data
public class FilmListResponse {
    String message;
    List<FilmResult> result;
}
