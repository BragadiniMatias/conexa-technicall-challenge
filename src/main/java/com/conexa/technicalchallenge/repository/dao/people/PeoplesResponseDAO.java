package com.conexa.technicalchallenge.repository.dao.people;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//TODO: Probablemente no sea el mejor nombre de la clase, refactorizar.
public class PeoplesResponseDAO {
    private String message;
    private int total_records;
    private int total_pages;
    private String previous;
    private String next;
    private List<PeoplesResultDAO> results;
}
