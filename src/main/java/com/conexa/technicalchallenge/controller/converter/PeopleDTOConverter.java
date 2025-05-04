package com.conexa.technicalchallenge.controller.converter;


import com.conexa.technicalchallenge.controller.dto.people.PeopleDTO;
import com.conexa.technicalchallenge.controller.dto.people.PeopleResponseDTO;
import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PeopleDTOConverter {

    PeopleDTO domainToDTO(People domain);

    List<PeopleDTO> domainListToDTOList(List<People> domainList);

    default PeopleResponseDTO wrapperToResponse(GenericContentPaginationWrapper<People> wrapper) {
        return new PeopleResponseDTO(
                domainListToDTOList( wrapper.getContent()),
                wrapper.getTotalPages(),
                wrapper.getTotalRecords()
        );
    }

}
