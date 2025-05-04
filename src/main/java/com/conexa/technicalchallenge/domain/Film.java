package com.conexa.technicalchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private String uid;
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
    private LocalDate created;
    private LocalDate edited;

}
