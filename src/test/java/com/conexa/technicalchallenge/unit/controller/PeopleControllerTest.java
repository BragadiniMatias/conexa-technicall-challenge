package com.conexa.technicalchallenge.unit.controller;

import com.conexa.technicalchallenge.controller.PeopleController;
import com.conexa.technicalchallenge.controller.converter.PeopleDTOConverter;
import com.conexa.technicalchallenge.controller.dto.people.PeopleDTO;
import com.conexa.technicalchallenge.controller.dto.people.PeopleResponseDTO;
import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.service.IPeopleService;
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
public class PeopleControllerTest {
    @Mock
    private IPeopleService service;

    @Mock
    private PeopleDTOConverter converter;

    @InjectMocks
    private PeopleController controller;


    @Test
    void getById_returnsPeopleDTO_whenValidIdProvided() {
        int id = 20;
        People mockDomain = new People();
        PeopleDTO dto = PeopleDTO.builder().uid("1").name("Luke Skywalker").build();

        when(service.getById(id)).thenReturn(mockDomain);
        when(converter.domainToDTO(mockDomain)).thenReturn(dto);

        ResponseEntity<PeopleDTO> response = controller.getById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getByName_returnsListOfPeopleDTO_whenNameExists() {
        String name = "CR90";
        List<People> mockDomainList = Collections.singletonList(new People());
        List<PeopleDTO> dtoList = Collections.singletonList(PeopleDTO.builder().name("CR90 corvette").build());

        when(service.getByName(name)).thenReturn(mockDomainList);
        when(converter.domainListToDTOList(mockDomainList)).thenReturn(dtoList);

        ResponseEntity<List<PeopleDTO>> response = controller.getByName(name);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dtoList, response.getBody());
    }

    @Test
    void getAllPeoples_returnsListOfPeoples_whenPageableProvided() {
        Pageable pageable = Pageable.unpaged();
        PeopleDTO peopleDTO = PeopleDTO.builder()
                .id("2")
                .name("CR90 corvette")
                .build();

        People people = People.builder()
                .id("2")
                .name("CR90 corvette")
                .build();

        GenericContentPaginationWrapper<People> wrapper = GenericContentPaginationWrapper.<People>builder()
                .content(Collections.singletonList(people))
                .build();

        PeopleResponseDTO response = PeopleResponseDTO.builder()
                .results(Collections.singletonList(peopleDTO))
                .build();

        when(service.getAll(pageable)).thenReturn(wrapper);
        when(converter.wrapperToResponse(wrapper)).thenReturn(response);

        ResponseEntity<PeopleResponseDTO> controllerResp = controller.getAll(pageable);

        assertEquals(200, controllerResp.getStatusCodeValue());
        assertEquals(1, controllerResp.getBody().getResults().size());

    }


    @Test
    void getById_returnsNotFoundException_whenPeopleNotFoundById(){
        int invalidId = 999999;

        when(service.getById(invalidId)).thenThrow(new DataNotFoundException("People not found for given id"));

        try {
            controller.getById(invalidId);
        } catch (DataNotFoundException ex) {
            assertEquals("People not found for given id", ex.getMessage());
        }

        verify(service).getById(invalidId);
        verifyNoInteractions(converter);


    }


}
