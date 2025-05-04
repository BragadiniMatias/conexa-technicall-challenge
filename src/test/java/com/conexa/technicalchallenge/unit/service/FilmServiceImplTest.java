package com.conexa.technicalchallenge.unit.service;

import com.conexa.technicalchallenge.domain.Film;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.repository.FIlmRepository;
import com.conexa.technicalchallenge.service.impl.FilmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmServiceImplTest {

    @Mock
    private FIlmRepository repository;

    @InjectMocks
    private FilmServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<Film> films = Collections.singletonList(new Film());
        when(repository.getAll()).thenReturn(films);

        List<Film> result = service.getAll();

        assertEquals(films, result);
        verify(repository).getAll();
    }

    @Test
    void testGetById() {
        int id = 1;
        Film film = new Film();
        when(repository.getById(id)).thenReturn(film);

        Film result = service.getById(id);

        assertEquals(film, result);
        verify(repository).getById(id);
    }

    @Test
    void testGetByNameFound() {
        Film film1 = new Film();
        film1.setTitle("A New Hope");

        Film film2 = new Film();
        film2.setTitle("The Empire Strikes Back");

        when(repository.getAll()).thenReturn(Arrays.asList(film1, film2));

        Film result = service.getByName("new");

        assertEquals(film1, result);
        verify(repository).getAll();
    }

    @Test
    void testGetByNameNotFound() {
        when(repository.getAll()).thenReturn(Collections.emptyList());

        assertThrows(DataNotFoundException.class, () -> service.getByName("Phantom"));
        verify(repository).getAll();
    }
}
