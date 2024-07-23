package com.conexa.technicalchallenge.repository;

import com.conexa.technicalchallenge.domain.Vehicle;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface VehicleRepository {
    List<Vehicle> getAll(final Pageable pageable);

    Vehicle getById(int id);
}
