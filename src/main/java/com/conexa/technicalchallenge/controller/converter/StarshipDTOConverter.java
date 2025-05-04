package com.conexa.technicalchallenge.controller.converter;

import com.conexa.technicalchallenge.controller.dto.people.PeopleResponseDTO;
import com.conexa.technicalchallenge.controller.dto.starship.StarshipDTO;
import com.conexa.technicalchallenge.controller.dto.starship.StarshipResponseDTO;
import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface StarshipDTOConverter {

    StarshipDTO domainToDTO(Starship domain);

    List<StarshipDTO> domainListToDTOList(List<Starship> domainList);

    default StarshipResponseDTO wrapperToResponse(GenericContentPaginationWrapper<Starship> wrapper) {
        return new StarshipResponseDTO(
                domainListToDTOList(wrapper.getContent()),
                wrapper.getTotalPages(),
                wrapper.getTotalRecords()
        );


    }
}
