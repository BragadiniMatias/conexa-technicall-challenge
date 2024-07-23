package com.conexa.technicalchallenge.service;

import com.conexa.technicalchallenge.domain.Film;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFilmService {

    List<Film> getAll(final Pageable pageable);

    Film getById(final int id);

}
