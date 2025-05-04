package com.conexa.technicalchallenge.controller;

import com.conexa.technicalchallenge.controller.converter.StarshipDTOConverter;
import com.conexa.technicalchallenge.controller.dto.people.PeopleDTO;
import com.conexa.technicalchallenge.controller.dto.starship.StarshipDTO;
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

    private final IStarshipService service;
    private final StarshipDTOConverter converter;

    @GetMapping
    public ResponseEntity<StarshipResponseDTO> getAll(final Pageable pageable) {
        StarshipResponseDTO response = converter.wrapperToResponse(service.getAll(pageable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StarshipDTO> getById(@PathVariable final int id){
        StarshipDTO response = converter.domainToDTO(service.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<StarshipDTO>> getByName(@RequestParam("name") final String name){
        List<StarshipDTO> response = converter.domainListToDTOList(service.getByName(name));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
