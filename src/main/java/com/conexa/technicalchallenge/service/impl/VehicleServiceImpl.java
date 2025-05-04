package com.conexa.technicalchallenge.service.impl;

import com.conexa.technicalchallenge.domain.Vehicle;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.repository.VehicleRepository;
import com.conexa.technicalchallenge.service.IVehiclesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehiclesService {

    private final VehicleRepository repository;

    @Override
    public GenericContentPaginationWrapper<Vehicle> getAll(final Pageable pageable) {
        return repository.getAll(pageable);
    }

    @Override
    public Vehicle getById(final int id) {
        return repository.getById(id);
    }

    @Override
    public List<Vehicle> getByName(final String name) {
        return repository.getByName(name);
    }


}
