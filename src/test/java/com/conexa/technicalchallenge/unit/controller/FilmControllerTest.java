package com.conexa.technicalchallenge.unit.controller;

import com.conexa.technicalchallenge.controller.FilmController;
import com.conexa.technicalchallenge.controller.converter.FilmDTOConverter;
import com.conexa.technicalchallenge.controller.dto.film.FilmResponseDTO;
import com.conexa.technicalchallenge.domain.Film;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.service.IFilmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilmControllerTest {
    @Mock
    private IFilmService service;

    @Mock
    private FilmDTOConverter converter;

    @InjectMocks
    private FilmController controller;


    @Test
    void getById_returnsFilmDTO_whenValidIdProvided() {
        int id = 20;
        Film mockDomain = new Film();
        FilmResponseDTO dto = FilmResponseDTO.builder().episodeId(4).title("A New Hope").build();

        when(service.getById(id)).thenReturn(mockDomain);
        when(converter.domainToDTO(mockDomain)).thenReturn(dto);

        ResponseEntity<FilmResponseDTO> response = controller.getById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getByName_returnsListOfFilmDTO_whenNameExists() {
        String name = "A New Hope";
        Film mockDomain = new Film();
        FilmResponseDTO dto = FilmResponseDTO.builder().episodeId(4).title("A New Hope").build();

        when(service.getByName(name)).thenReturn(mockDomain);
        when(converter.domainToDTO(mockDomain)).thenReturn(dto);

        ResponseEntity<FilmResponseDTO> response = controller.getByName(name);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getById_returnsNotFoundException_whenFilmNotFoundById(){
        int invalidId = 999999;

        when(service.getById(invalidId)).thenThrow(new DataNotFoundException("Film not found for given id"));

        try {
            controller.getById(invalidId);
        } catch (DataNotFoundException ex) {
            assertEquals("Film not found for given id", ex.getMessage());
        }

        verify(service).getById(invalidId);
        //Nno se deberia invocar nunca porque no tiene que pasar x el converter en este punto
        verifyNoInteractions(converter);


    }


}
