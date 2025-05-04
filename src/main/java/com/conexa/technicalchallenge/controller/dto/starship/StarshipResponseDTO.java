package com.conexa.technicalchallenge.controller.dto.starship;

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
public class StarshipResponseDTO {
    public List<StarshipDTO> results;
    public int totalRecords;
    public int totalPages;
}
