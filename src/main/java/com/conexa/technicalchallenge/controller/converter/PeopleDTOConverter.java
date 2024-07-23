package com.conexa.technicalchallenge.controller.converter;


import com.conexa.technicalchallenge.controller.dto.people.PeopleResponseDTO;
import com.conexa.technicalchallenge.domain.People;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PeopleDTOConverter {

    PeopleResponseDTO domainToDTO(People domain);

    List<PeopleResponseDTO> domainListToDTOList(List<People> domainList);

}
