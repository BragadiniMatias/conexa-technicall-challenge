package com.conexa.technicalchallenge.repository.dao.film;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.ArrayList;

@Data
public class FilmProperties {
    public String created;
    public String edited;
    public ArrayList<String> starships;
    public ArrayList<String> vehicles;
    public ArrayList<String> planets;
    public String producer;
    public String title;
    @JsonProperty("episode_id")
    public int episodeId;
    public String director;
    @JsonProperty("release_date")
    public String releaseDate;
    @JsonProperty("opening_crawl")
    public String openingCrawl;
    public ArrayList<String> characters;
    public ArrayList<String> species;
    public String url;
}
