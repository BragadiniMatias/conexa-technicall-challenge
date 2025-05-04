package com.conexa.technicalchallenge.unit.service;

import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.repository.StarshipRepository;
import com.conexa.technicalchallenge.service.impl.StarshipServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StarshipServiceImplTest {

    @Mock
    private StarshipRepository repository;

    @InjectMocks
    private StarshipServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Starship> starships = Collections.singletonList(new Starship());
        GenericContentPaginationWrapper<Starship> wrapper =
                GenericContentPaginationWrapper.<Starship>builder()
                        .content(starships)
                        .build();

        when(repository.getAll(pageable)).thenReturn(wrapper);

        GenericContentPaginationWrapper result = service.getAll(pageable);

        assertEquals(wrapper, result);
        verify(repository).getAll(pageable);
    }

    @Test
    void testGetById() {
        int id = 1;
        Starship starship = new Starship();
        when(repository.getById(id)).thenReturn(starship);

        Starship result = service.getById(id);

        assertEquals(starship, result);
        verify(repository).getById(id);
    }

    @Test
    void testGetByName() {
        String name = "Millennium Falcon";
        List<Starship> starships = Collections.singletonList(new Starship());
        when(repository.getByName(name)).thenReturn(starships);

        List<Starship> result = service.getByName(name);

        assertEquals(starships, result);
        verify(repository).getByName(name);
    }
}
