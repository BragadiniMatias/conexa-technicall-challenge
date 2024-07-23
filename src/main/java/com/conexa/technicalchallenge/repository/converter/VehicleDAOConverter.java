package com.conexa.technicalchallenge.repository.converter;

import com.conexa.technicalchallenge.domain.Vehicle;
import com.conexa.technicalchallenge.repository.dao.vehicle.VehicleDAO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface VehicleDAOConverter {
    Vehicle daoToDomain(VehicleDAO starshipDAO);

    List<Vehicle> daoListToDomainList(List<VehicleDAO> daoList);
}
