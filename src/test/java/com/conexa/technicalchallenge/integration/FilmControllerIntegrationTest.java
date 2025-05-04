package com.conexa.technicalchallenge.integration;

import com.conexa.technicalchallenge.controller.converter.FilmDTOConverter;

import com.conexa.technicalchallenge.controller.dto.film.FilmResponseDTO;
import com.conexa.technicalchallenge.domain.Film;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.service.IFilmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class FilmControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFilmService service;

    @MockBean
    private FilmDTOConverter converter;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testGetById() throws Exception {
        int id = 1;
        Film domain = new Film();
        FilmResponseDTO filmDTO = FilmResponseDTO.builder()
                .episodeId(4)
                .title("A New Hope")
                .build();

        when(service.getById(id)).thenReturn(domain);
        when(converter.domainToDTO(domain)).thenReturn(filmDTO);

        mockMvc.perform(get("/films/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("A New Hope"));
    }

    @Test
    public void testGetByName() throws Exception {
        String name = "A New Hope";
        Film film = new Film();
        FilmResponseDTO dto =
                FilmResponseDTO.builder()
                        .episodeId(2)
                        .title("A New Hope")
                        .build();

        when(service.getByName(name)).thenReturn(film);
        when(converter.domainToDTO(film)).thenReturn(dto);

        mockMvc.perform(get("/films/name").param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("A New Hope"));
    }
}
