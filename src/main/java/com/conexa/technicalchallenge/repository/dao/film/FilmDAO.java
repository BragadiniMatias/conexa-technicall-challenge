package com.conexa.technicalchallenge.repository.dao.film;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDAO {

    @JsonProperty("uid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonProperty("characters")
    private List<String> characters;

    @JsonProperty("planets")
    private List<String> planets;

    @JsonProperty("starships")
    private List<String> starships;

    @JsonProperty("vehicles")
    private List<String> vehicles;

    @JsonProperty("species")
    private List<String> species;

    @JsonProperty("created")
    private String created;

    @JsonProperty("edited")
    private String edited;

    @JsonProperty("producer")
    private String producer;

    @JsonProperty("title")
    private String title;

    @JsonProperty("episode_id")
    private int episodeId;

    @JsonProperty("director")
    private String director;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("opening_crawl")
    private String openingCrawl;

    @JsonProperty("url")
    private String url;

}
