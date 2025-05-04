package com.conexa.technicalchallenge.service.impl;

import com.conexa.technicalchallenge.domain.Vehicle;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VehicleServiceImplTest {

    @Mock
    private VehicleRepository repository;

    @InjectMocks
    private VehicleServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Vehicle> vehicles = Collections.singletonList(new Vehicle());
        GenericContentPaginationWrapper<Vehicle> wrapper =
                GenericContentPaginationWrapper.<Vehicle>builder()
                        .content(vehicles)
                        .build();

        when(repository.getAll(pageable)).thenReturn(wrapper);

        GenericContentPaginationWrapper<Vehicle> result = service.getAll(pageable);

        assertEquals(wrapper, result);
        verify(repository).getAll(pageable);
    }

    @Test
    void testGetById() {
        int id = 1;
        Vehicle vehicle = new Vehicle();
        when(repository.getById(id)).thenReturn(vehicle);

        Vehicle result = service.getById(id);

        assertEquals(vehicle, result);
        verify(repository).getById(id);
    }

    @Test
    void testGetByName() {
        String name = "Speeder";
        List<Vehicle> vehicles = Collections.singletonList(new Vehicle());
        when(repository.getByName(name)).thenReturn(vehicles);

        List<Vehicle> result = service.getByName(name);

        assertEquals(vehicles, result);
        verify(repository).getByName(name);
    }
}
