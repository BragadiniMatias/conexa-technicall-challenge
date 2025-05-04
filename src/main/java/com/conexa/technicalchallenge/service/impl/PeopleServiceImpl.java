package com.conexa.technicalchallenge.service.impl;

import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.repository.PeopleRepository;
import com.conexa.technicalchallenge.service.IPeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeopleServiceImpl implements IPeopleService {

    private final PeopleRepository repository;

    @Override
    public GenericContentPaginationWrapper<People> getAll(final Pageable pageable) {
        return repository.getAll(pageable);
    }

    @Override
    public People getById(final int id) {
        return repository.getById(id);
    }

    @Override
    public List<People> getByName(final String name) {
        return repository.getByName(name);
    }


}
