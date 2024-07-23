package com.conexa.technicalchallenge.repository.dao.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiclesResponseDAO {
    private String message;
    private int total_records;
    private int total_pages;
    private String previous;
    private String next;
    List<VehiclesResultDAO> results;
}
