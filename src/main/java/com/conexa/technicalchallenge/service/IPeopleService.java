package com.conexa.technicalchallenge.service;

import com.conexa.technicalchallenge.domain.People;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IPeopleService {
    List<People> getAll(final Pageable pageable);
    People getById(int id);

}
