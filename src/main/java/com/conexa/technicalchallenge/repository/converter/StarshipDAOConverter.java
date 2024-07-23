package com.conexa.technicalchallenge.repository.converter;

import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.repository.dao.starship.StarshipDAO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface StarshipDAOConverter {
    Starship daoToDomain(StarshipDAO starshipDAO);

    List<Starship> daoListToDomainList(List<StarshipDAO> daoList);

}
