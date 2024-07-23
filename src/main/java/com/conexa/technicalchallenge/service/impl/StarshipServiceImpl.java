package com.conexa.technicalchallenge.service.impl;

import com.conexa.technicalchallenge.domain.Starship;
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
    public List<Starship> getAll(final Pageable pageable) {
        return repository.getAll(pageable);
    }

    @Override
    public Starship getById(final int id) {
        return repository.getById(id);
    }
}
