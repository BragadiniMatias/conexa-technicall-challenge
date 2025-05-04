package com.conexa.technicalchallenge.service.impl;

import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.repository.StarshipRepository;
import com.conexa.technicalchallenge.service.IStarshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StarshipServiceImpl  implements IStarshipService {

    @Autowired
    StarshipRepository repository;

    @Override
    public GenericContentPaginationWrapper getAll(final Pageable pageable) {
        return repository.getAll(pageable);
    }

    @Override
    public Starship getById(final int id) {
        return repository.getById(id);
    }

    @Override
    public List<Starship> getByName(final String name) {
        return repository.getByName(name);
    }
}
