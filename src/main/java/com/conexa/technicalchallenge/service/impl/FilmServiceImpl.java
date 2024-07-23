package com.conexa.technicalchallenge.service.impl;


import com.conexa.technicalchallenge.domain.Film;
import com.conexa.technicalchallenge.repository.FilmsRepository;
import com.conexa.technicalchallenge.service.IFilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements IFilmService {

    @Autowired
    FilmsRepository repository;

    @Override
    public List<Film> getAll(final Pageable pageable) {
        return repository.getAll(pageable);
    }

    @Override
    public Film getById(final int id) {
        return repository.getById(id);
    }

}
