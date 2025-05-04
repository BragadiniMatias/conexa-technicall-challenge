package com.conexa.technicalchallenge.repository.dao.vehicle;

import lombok.Data;

import java.util.List;

@Data
public class VehicleSearchResponse {
    private String message;
    private List<VehicleResult> result;
}
