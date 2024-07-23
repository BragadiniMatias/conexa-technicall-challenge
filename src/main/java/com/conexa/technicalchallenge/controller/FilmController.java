package com.conexa.technicalchallenge.controller;


import com.conexa.technicalchallenge.controller.converter.FilmDTOConverter;
import com.conexa.technicalchallenge.controller.dto.film.FilmResponseDTO;
import com.conexa.technicalchallenge.service.IFilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    @Autowired
    private IFilmService service;

    private final FilmDTOConverter converter;

    @GetMapping
    ResponseEntity<List<FilmResponseDTO>> getAll(final Pageable pageable){
        List<FilmResponseDTO> response = converter.domainListToDTOList(service.getAll(pageable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<FilmResponseDTO> getById(@PathVariable("id") final int id){
        FilmResponseDTO response = converter.domainToDTO(service.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
