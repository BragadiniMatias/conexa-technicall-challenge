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

    private final IFilmService service;
    private final FilmDTOConverter converter;

    @GetMapping
    public ResponseEntity<List<FilmResponseDTO>> getAll(){
        List<FilmResponseDTO> response = converter.domainListToDTOList(service.getAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmResponseDTO> getById(@PathVariable("id") final int id){
        FilmResponseDTO response = converter.domainToDTO(service.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<FilmResponseDTO> getByName(@RequestParam("name") final String name){
        FilmResponseDTO response = converter.domainToDTO(service.getByName(name));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
