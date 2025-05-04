package com.conexa.technicalchallenge.repository;
import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PeopleRepository {
    GenericContentPaginationWrapper<People> getAll(final Pageable pageable);

    People getById(final int id);

    List<People> getByName(final String name);

}
