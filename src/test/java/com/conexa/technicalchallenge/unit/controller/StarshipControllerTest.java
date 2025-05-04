package com.conexa.technicalchallenge.unit.controller;

import com.conexa.technicalchallenge.controller.StarshipController;
import com.conexa.technicalchallenge.controller.converter.StarshipDTOConverter;
import com.conexa.technicalchallenge.controller.dto.starship.StarshipDTO;
import com.conexa.technicalchallenge.controller.dto.starship.StarshipResponseDTO;
import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.service.IStarshipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StarshipControllerTest {
    @Mock
    private IStarshipService service;

    @Mock
    private StarshipDTOConverter converter;

    @InjectMocks
    private StarshipController controller;


    @Test
    void getById_returnsStarshipDTO_whenValidIdProvided() {
        int id = 20;
        Starship mockDomain = new Starship();
        StarshipDTO dto = StarshipDTO.builder().uid("20").name("CR90 corvette").build();

        when(service.getById(id)).thenReturn(mockDomain);
        when(converter.domainToDTO(mockDomain)).thenReturn(dto);

        ResponseEntity<StarshipDTO> response = controller.getById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getByName_returnsListOfStarshipDTO_whenNameExists() {
        String name = "CR90";
        List<Starship> mockDomainList = Collections.singletonList(new Starship());
        List<StarshipDTO> dtoList = Collections.singletonList(StarshipDTO.builder().name("CR90 corvette").build());

        when(service.getByName(name)).thenReturn(mockDomainList);
        when(converter.domainListToDTOList(mockDomainList)).thenReturn(dtoList);

        ResponseEntity<List<StarshipDTO>> response = controller.getByName(name);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dtoList, response.getBody());
    }

    @Test
    void getAllStarships_returnsListOfStarships_whenPageableProvided() {
        Pageable pageable = Pageable.unpaged();
        StarshipDTO starshipDTO = StarshipDTO.builder()
                .id("2")
                .name("CR90 corvette")
                .build();

        Starship starship = Starship.builder()
                .id("2")
                .name("CR90 corvette")
                .build();

        GenericContentPaginationWrapper<Starship> wrapper = GenericContentPaginationWrapper.<Starship>builder()
                .content(Collections.singletonList(starship))
                .build();

        StarshipResponseDTO response = StarshipResponseDTO.builder()
                .results(Collections.singletonList(starshipDTO))
                .build();

        when(service.getAll(pageable)).thenReturn(wrapper);
        when(converter.wrapperToResponse(wrapper)).thenReturn(response);

        ResponseEntity<StarshipResponseDTO> controllerResp = controller.getAll(pageable);

        assertEquals(200, controllerResp.getStatusCodeValue());
        assertEquals(1, controllerResp.getBody().getResults().size());

    }


    @Test
    void getById_returnsNotFoundException_whenStarshipNotFoundById(){
        int invalidId = 999999;

        when(service.getById(invalidId)).thenThrow(new DataNotFoundException("Starship not found for given id"));

        try {
            controller.getById(invalidId);
        } catch (DataNotFoundException ex) {
            assertEquals("Starship not found for given id", ex.getMessage());
        }

        verify(service).getById(invalidId);
        //Nno se deberia invocar nunca porque no tiene que pasar x el converter en este punto
        verifyNoInteractions(converter);


    }


}
