package com.conexa.technicalchallenge.repository.dao.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiclesResultDAO {
    @JsonProperty("uid")
    private String id;
    private String name;
    private String url;
}
