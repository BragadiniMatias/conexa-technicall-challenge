package com.conexa.technicalchallenge.service;

import com.conexa.technicalchallenge.domain.Vehicle;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IVehiclesService {
    List<Vehicle> getAll(final Pageable pageable);

    Vehicle getById(int id);

}
