package com.conexa.technicalchallenge.service.impl;

import com.conexa.technicalchallenge.domain.People;
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

    @Autowired
    PeopleRepository repository;

    @Override
    public List<People> getAll(final Pageable pageable) {
        return repository.getAll(pageable);
    }

    @Override
    public People getById(final int id) {
        return repository.getById(id);
    }

}
