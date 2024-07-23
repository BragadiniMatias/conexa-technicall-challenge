package com.conexa.technicalchallenge.controller;

import com.conexa.technicalchallenge.controller.converter.PeopleDTOConverter;
import com.conexa.technicalchallenge.controller.dto.people.PeopleResponseDTO;
import com.conexa.technicalchallenge.service.IPeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {

    @Autowired
    IPeopleService service;

    private final PeopleDTOConverter converter;

    @GetMapping
    public ResponseEntity<List<PeopleResponseDTO>> getAll(final Pageable pageable) {
        List<PeopleResponseDTO> response = converter.domainListToDTOList(service.getAll(pageable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<PeopleResponseDTO> getById(@PathVariable final int id){
        PeopleResponseDTO response = converter.domainToDTO(service.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
