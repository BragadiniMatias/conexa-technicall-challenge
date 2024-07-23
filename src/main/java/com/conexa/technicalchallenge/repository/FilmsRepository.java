package com.conexa.technicalchallenge.repository;

import com.conexa.technicalchallenge.domain.Film;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilmsRepository {
    Film getById(int id);

    List<Film> getAll(final Pageable pageable);

}
