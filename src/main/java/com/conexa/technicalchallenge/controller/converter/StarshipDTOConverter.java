package com.conexa.technicalchallenge.controller.converter;

import com.conexa.technicalchallenge.controller.dto.starship.StarshipResponseDTO;
import com.conexa.technicalchallenge.domain.Starship;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface StarshipDTOConverter {

    StarshipResponseDTO domainToDTO(Starship domain);

    List<StarshipResponseDTO> domainListToDTOList(List<Starship> domainList);

}
