package com.conexa.technicalchallenge.controller.dto.vehicle;

import com.conexa.technicalchallenge.controller.dto.starship.StarshipDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleResponseDTO {
    public List<VehicleDTO> results;
    public int totalRecords;
    public int totalPages;
}
