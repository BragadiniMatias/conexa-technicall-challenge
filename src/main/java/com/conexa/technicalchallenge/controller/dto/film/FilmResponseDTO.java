package com.conexa.technicalchallenge.controller.dto.film;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmResponseDTO {
    private int episodeId;
    private String title;
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
