package com.conexa.technicalchallenge.repository;

import com.conexa.technicalchallenge.domain.Film;

import java.util.List;

public interface FIlmRepository {
    Film getById(final int id);

    List<Film> getAll();

}
