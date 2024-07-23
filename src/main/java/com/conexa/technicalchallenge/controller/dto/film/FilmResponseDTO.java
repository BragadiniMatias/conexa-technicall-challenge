package com.conexa.technicalchallenge.controller.dto.film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmResponseDTO {
    private String id;
    private String title;
    private int episodeId;
    private String openingCrawl;
    private String director;
    private String producer;
    private LocalDate releaseDate;
    private List<String> characters;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
    private String url;
    private String created;
    private String edited;

}
