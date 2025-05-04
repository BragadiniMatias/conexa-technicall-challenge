package com.conexa.technicalchallenge.controller;


import com.conexa.technicalchallenge.controller.converter.VehicleDTOConverter;
import com.conexa.technicalchallenge.controller.dto.vehicle.VehicleDTO;
import com.conexa.technicalchallenge.controller.dto.vehicle.VehicleResponseDTO;
import com.conexa.technicalchallenge.service.IVehiclesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final IVehiclesService service;
    private final VehicleDTOConverter converter;

    @GetMapping
    public ResponseEntity<VehicleResponseDTO> getAll(final Pageable pageable) {
        VehicleResponseDTO response = converter.wrapperToResponse(service.getAll(pageable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getById(@PathVariable final int id){
        VehicleDTO response = converter.domainToDTO(service.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<VehicleDTO>> getByName(@RequestParam("name") final String name){
        List<VehicleDTO> response = converter.listDomainToDTOList(service.getByName(name));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
