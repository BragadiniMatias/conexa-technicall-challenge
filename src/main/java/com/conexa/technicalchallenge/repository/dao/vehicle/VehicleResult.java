package com.conexa.technicalchallenge.repository.dao.vehicle;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VehicleResult {
    private VehicleProperties properties;
    private String description;
    private String uid;
    @JsonProperty("_id")
    private String id;
    @JsonProperty("__v")
    private int version;
}
