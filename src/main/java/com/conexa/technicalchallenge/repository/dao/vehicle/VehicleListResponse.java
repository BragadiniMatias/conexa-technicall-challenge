package com.conexa.technicalchallenge.repository.dao.vehicle;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VehicleListResponse {
    private String message;
    @JsonProperty("total_records")
    private int totalRecords;
    @JsonProperty("total_pages")
    private int totalPages;
    private String previous;
    private String next;
    private List<VehicleListItem> results;
}
