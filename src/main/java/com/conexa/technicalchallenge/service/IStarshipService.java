package com.conexa.technicalchallenge.service;

import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStarshipService {
    GenericContentPaginationWrapper<Starship> getAll(final Pageable pageable);
    Starship getById(int id);
    List<Starship> getByName(final String name);
}
