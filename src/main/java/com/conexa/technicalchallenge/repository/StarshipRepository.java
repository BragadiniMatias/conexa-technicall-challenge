package com.conexa.technicalchallenge.repository;

import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StarshipRepository {
    GenericContentPaginationWrapper<Starship> getAll(final Pageable pageable);

    Starship getById(int id);

    List<Starship> getByName(final String name);
}
