package com.conexa.technicalchallenge.repository;

import com.conexa.technicalchallenge.domain.Starship;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StarshipRepository {
    List<Starship> getAll(final Pageable pageable);

    Starship getById(int id);
}
