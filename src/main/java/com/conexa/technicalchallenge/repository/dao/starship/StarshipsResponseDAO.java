package com.conexa.technicalchallenge.repository.dao.starship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarshipsResponseDAO {
    private String message;
    private int total_records;
    private int total_pages;
    private String previous;
    private String next;
    private List<StarshipsResultDAO> results;
}
