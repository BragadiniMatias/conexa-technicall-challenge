package com.conexa.technicalchallenge.controller;

import com.conexa.technicalchallenge.controller.converter.PeopleDTOConverter;
import com.conexa.technicalchallenge.controller.converter.StarshipDTOConverter;
import com.conexa.technicalchallenge.controller.dto.people.PeopleResponseDTO;
import com.conexa.technicalchallenge.controller.dto.starship.StarshipResponseDTO;
import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.service.IPeopleService;
import com.conexa.technicalchallenge.service.IStarshipService;
import com.conexa.technicalchallenge.service.impl.PeopleServiceImpl;
import com.conexa.technicalchallenge.service.impl.StarshipServiceImpl;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class StarshipControllerTest {
    private final IStarshipService service = mock(StarshipServiceImpl.class);
    private final StarshipDTOConverter converter = mock(StarshipDTOConverter.class);

    @InjectMocks
    private final StarshipController sut = new StarshipController(converter);

    private final Starship domain = mock(Starship.class);
    private final List<Starship> domainList = Collections.singletonList(domain);
    private final StarshipResponseDTO responseDTO = mock(StarshipResponseDTO.class);
    private final List<StarshipResponseDTO> responseDTOList =  Collections.singletonList(responseDTO);

    @Test
    void whenGetAll_thenCallServiceAndConverterAndReturnResultList(){
        when(service.getAll(any(Pageable.class))).thenReturn(domainList);
        when(converter.domainListToDTOList(domainList)).thenReturn(responseDTOList);

        final ResponseEntity<List<StarshipResponseDTO>> result = sut.getAll(Pageable.unpaged());

        assertEquals(responseDTOList, result.getBody());
        verify(service, times(1)).getAll(any(Pageable.class));
        verify(converter, times(1)).domainListToDTOList(domainList);
    }

    @Test
    void givenIdAndName_whenGetBy_thenCallServiceAndConverterAndReturnResult(){
        when(service.getById(VALID_ID)).thenReturn(domain);
        when(converter.domainToDTO(domain)).thenReturn(responseDTO);

        final ResponseEntity<StarshipResponseDTO> result = sut.getById(VALID_ID);

        assertEquals(responseDTO, result.getBody());
        verify(service, times(1)).getById(VALID_ID);
        verify(converter, times(1)).domainToDTO(domain);
    }

    @Test
    void givenNoData_whenGetBy_thenCallServiceAndConverterAndReturnResult(){
        when(service.getById(1)).thenReturn(domain);
        when(converter.domainToDTO(domain)).thenReturn(responseDTO);

        final ResponseEntity<StarshipResponseDTO> result = sut.getById(1);

        assertEquals(responseDTO, result.getBody());
        verify(service, times(1)).getById(1);
        verify(converter, times(1)).domainToDTO(domain);
    }
}

