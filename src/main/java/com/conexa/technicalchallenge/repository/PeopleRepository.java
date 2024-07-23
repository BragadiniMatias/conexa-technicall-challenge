package com.conexa.technicalchallenge.repository;
import com.conexa.technicalchallenge.domain.People;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PeopleRepository {
    List<People> getAll(final Pageable pageable);

    People getById(int id);

}
