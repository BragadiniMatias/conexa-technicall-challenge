package com.conexa.technicalchallenge.service;

import com.conexa.technicalchallenge.domain.Starship;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStarshipService {
    List<Starship> getAll(final Pageable pageable);
    Starship getById(int id);
}
