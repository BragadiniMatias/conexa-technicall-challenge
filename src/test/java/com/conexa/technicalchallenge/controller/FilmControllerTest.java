package com.conexa.technicalchallenge.controller;

import com.conexa.technicalchallenge.controller.converter.FilmDTOConverter;
import com.conexa.technicalchallenge.controller.dto.film.FilmResponseDTO;
import com.conexa.technicalchallenge.domain.Film;
import com.conexa.technicalchallenge.service.IFilmService;
import com.conexa.technicalchallenge.service.impl.FilmServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static com.conexa.technicalchallenge.utils.constants.TestsConstants.VALID_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilmControllerTest {

    private final IFilmService service = mock(FilmServiceImpl.class);
    private final FilmDTOConverter converter = mock(FilmDTOConverter.class);

    @InjectMocks
    private final FilmController sut = new FilmController(converter);

    private final Film domain = mock(Film.class);
    private final List<Film> domainList = Collections.singletonList(domain);
    private final FilmResponseDTO responseDTO = mock(FilmResponseDTO.class);
    private final List<FilmResponseDTO> responseDTOList =  Collections.singletonList(responseDTO);

    @Test
    void whenGetAll_thenCallServiceAndConverterAndReturnResultList(){
        when(service.getAll(any(Pageable.class))).thenReturn(domainList);
        when(converter.domainListToDTOList(domainList)).thenReturn(responseDTOList);

        final ResponseEntity<List<FilmResponseDTO>> result = sut.getAll(Pageable.unpaged());

        assertEquals(responseDTOList, result.getBody());
        verify(service, times(1)).getAll(any(Pageable.class));
        verify(converter, times(1)).domainListToDTOList(domainList);
    }

    @Test
    void givenIdAndName_whenGetBy_thenCallServiceAndConverterAndReturnResult(){
        when(service.getById(VALID_ID)).thenReturn(domain);
        when(converter.domainToDTO(domain)).thenReturn(responseDTO);

        final ResponseEntity<FilmResponseDTO> result = sut.getById(VALID_ID);

        assertEquals(responseDTO, result.getBody());
        verify(service, times(1)).getById(VALID_ID);
        verify(converter, times(1)).domainToDTO(domain);
    }

    @Test
    void givenNoData_whenGetBy_thenCallServiceAndConverterAndReturnResult(){
        when(service.getById(1)).thenReturn(domain);
        when(converter.domainToDTO(domain)).thenReturn(responseDTO);

        final ResponseEntity<FilmResponseDTO> result = sut.getById(1);

        assertEquals(responseDTO, result.getBody());
        verify(service, times(1)).getById(1);
        verify(converter, times(1)).domainToDTO(domain);
    }

}