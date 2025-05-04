package com.conexa.technicalchallenge.controller;

import com.conexa.technicalchallenge.controller.converter.PeopleDTOConverter;
import com.conexa.technicalchallenge.controller.dto.people.PeopleDTO;
import com.conexa.technicalchallenge.controller.dto.people.PeopleResponseDTO;
import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
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

    private final IPeopleService service;
    private final PeopleDTOConverter converter;

    @GetMapping
    public ResponseEntity<PeopleResponseDTO> getAll(final Pageable pageable) {
        PeopleResponseDTO res = converter.wrapperToResponse(service.getAll(pageable));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeopleDTO> getById(@PathVariable("id") final int id){
        PeopleDTO response = converter.domainToDTO(service.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<PeopleDTO>> getByName(@RequestParam("name") final String name){
        List<PeopleDTO> response = converter.domainListToDTOList(service.getByName(name));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
