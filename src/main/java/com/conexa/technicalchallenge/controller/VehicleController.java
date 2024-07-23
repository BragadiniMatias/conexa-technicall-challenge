package com.conexa.technicalchallenge.controller;

import com.conexa.technicalchallenge.controller.converter.VehicleDTOConverter;
import com.conexa.technicalchallenge.controller.dto.vehicle.VehicleResponseDTO;
import com.conexa.technicalchallenge.service.IVehiclesService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    @Autowired
    IVehiclesService service;

    private final VehicleDTOConverter converter;

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAll(final Pageable pageable) {
        List<VehicleResponseDTO> response = converter.listDomainToDTOList(service.getAll(pageable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<VehicleResponseDTO> getById(@PathVariable final int id){
        VehicleResponseDTO response = converter.domainToDTO(service.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
