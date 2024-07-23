package com.conexa.technicalchallenge.controller.converter;

import com.conexa.technicalchallenge.controller.dto.vehicle.VehicleResponseDTO;
import com.conexa.technicalchallenge.domain.Vehicle;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface VehicleDTOConverter {

    VehicleResponseDTO domainToDTO(Vehicle domain);

    List<VehicleResponseDTO> listDomainToDTOList(List<Vehicle> domainList);

}
