package com.conexa.technicalchallenge.service.impl;

import com.conexa.technicalchallenge.domain.Film;
import com.conexa.technicalchallenge.repository.FilmsRepository;
import com.conexa.technicalchallenge.service.IFilmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static com.conexa.technicalchallenge.utils.constants.TestsConstants.VALID_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {

    private final FilmsRepository repository = mock(FilmsRepository.class);

    @InjectMocks
    private final IFilmService sut = new FilmServiceImpl();

    private final Film domain = mock(Film.class);
    private final List<Film> domainList =  Collections.singletonList(domain);

    @Test
    void whenGetAll_thenCallServiceAndConverterAndReturnResultList(){
        when(repository.getAll(any(Pageable.class))).thenReturn(domainList);

        final List<Film> result = sut.getAll(Pageable.unpaged());

        assertEquals(domainList, result);
        verify(repository, times(1)).getAll(any(Pageable.class));
    }

    @Test
    void givenId_whenGetBy_thenCallServiceAndConverterAndReturnResult(){
        when(repository.getById(VALID_ID)).thenReturn(domain);

        final Film result = sut.getById(VALID_ID);

        assertEquals(domain, result);
        verify(repository, times(1)).getById(VALID_ID);
    }

}