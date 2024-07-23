package com.conexa.technicalchallenge.controller;

import com.conexa.technicalchallenge.controller.converter.StarshipDTOConverter;
import com.conexa.technicalchallenge.controller.dto.starship.StarshipResponseDTO;
import com.conexa.technicalchallenge.service.IStarshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/starships")
public class StarshipController {

    @Autowired
    IStarshipService service;

    private final StarshipDTOConverter converter;

    @GetMapping
    public ResponseEntity<List<StarshipResponseDTO>> getAll(final Pageable pageable) {
        List<StarshipResponseDTO> response = converter.domainListToDTOList(service.getAll(pageable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<StarshipResponseDTO> getById(@PathVariable final int id){
        StarshipResponseDTO response = converter.domainToDTO(service.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
