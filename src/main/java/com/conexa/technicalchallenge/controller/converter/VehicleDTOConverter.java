package com.conexa.technicalchallenge.controller.converter;

import com.conexa.technicalchallenge.controller.dto.starship.StarshipResponseDTO;
import com.conexa.technicalchallenge.controller.dto.vehicle.VehicleDTO;
import com.conexa.technicalchallenge.controller.dto.vehicle.VehicleResponseDTO;
import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.domain.Vehicle;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface VehicleDTOConverter {

    VehicleDTO domainToDTO(Vehicle domain);

    List<VehicleDTO> listDomainToDTOList(List<Vehicle> domainList);

    default VehicleResponseDTO wrapperToResponse(GenericContentPaginationWrapper<Vehicle> wrapper) {
        return new VehicleResponseDTO(
                listDomainToDTOList(wrapper.getContent()),
                wrapper.getTotalPages(),
                wrapper.getTotalRecords()
        );

    }
}
