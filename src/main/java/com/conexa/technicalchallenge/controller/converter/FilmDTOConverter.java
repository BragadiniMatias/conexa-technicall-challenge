package com.conexa.technicalchallenge.controller.converter;

import com.conexa.technicalchallenge.controller.dto.film.FilmResponseDTO;
import com.conexa.technicalchallenge.domain.Film;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface FilmDTOConverter {

    @Mapping(source = "releaseDate", target = "releaseDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "edited", target = "edited", dateFormat = "yyyy-MM-dd")
    FilmResponseDTO domainToDTO(Film domain);

    List<FilmResponseDTO> domainListToDTOList(List<Film> domainList);

}
