package com.conexa.technicalchallenge.service;

import com.conexa.technicalchallenge.domain.Vehicle;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IVehiclesService {
    GenericContentPaginationWrapper<Vehicle> getAll(final Pageable pageable);

    Vehicle getById(int id);

    List<Vehicle> getByName(final String name);

}
