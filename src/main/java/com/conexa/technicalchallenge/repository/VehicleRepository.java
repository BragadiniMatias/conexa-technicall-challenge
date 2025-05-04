package com.conexa.technicalchallenge.repository;

import com.conexa.technicalchallenge.domain.Vehicle;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface VehicleRepository {
    GenericContentPaginationWrapper<Vehicle> getAll(final Pageable pageable);

    Vehicle getById(final int id);

    List<Vehicle> getByName(final String name);
}
