package com.conexa.technicalchallenge.service.impl;


import com.conexa.technicalchallenge.domain.Film;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.repository.FIlmRepository;
import com.conexa.technicalchallenge.service.IFilmService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements IFilmService {

    private final FIlmRepository repository;

    @Override
    public List<Film> getAll() {
        return repository.getAll();
    }

    @Override
    public Film getById(final int id) {
        return repository.getById(id);
    }

    @Override
    public Film getByName(final String name){
        List<Film> films = repository.getAll();
        return films.stream()
                .filter(film -> StringUtils.containsIgnoreCase(film.getTitle(), name))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No film found with title: " + name));

    }

}
