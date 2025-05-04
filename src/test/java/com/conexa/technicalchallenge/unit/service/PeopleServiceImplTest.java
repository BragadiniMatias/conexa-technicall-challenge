package com.conexa.technicalchallenge.unit.service;

import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.repository.PeopleRepository;
import com.conexa.technicalchallenge.service.impl.PeopleServiceImpl;
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

class PeopleServiceImplTest {

    @Mock
    private PeopleRepository repository;

    @InjectMocks
    private PeopleServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<People> peopleList = Collections.singletonList(new People());
        GenericContentPaginationWrapper<People> wrapper =
                GenericContentPaginationWrapper.<People>builder()
                        .content(peopleList)
                        .build();

        when(repository.getAll(pageable)).thenReturn(wrapper);

        GenericContentPaginationWrapper<People> result = service.getAll(pageable);

        assertEquals(wrapper, result);
        verify(repository).getAll(pageable);
    }

    @Test
    void testGetById() {
        int id = 42;
        People person = new People();

        when(repository.getById(id)).thenReturn(person);

        People result = service.getById(id);

        assertEquals(person, result);
        verify(repository).getById(id);
    }

    @Test
    void testGetByName() {
        String name = "Luke Skywalker";
        List<People> peopleList = Collections.singletonList(new People());

        when(repository.getByName(name)).thenReturn(peopleList);

        List<People> result = service.getByName(name);

        assertEquals(peopleList, result);
        verify(repository).getByName(name);
    }
}
