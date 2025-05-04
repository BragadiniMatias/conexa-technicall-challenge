package com.conexa.technicalchallenge.integration;

import com.conexa.technicalchallenge.controller.converter.PeopleDTOConverter;
import com.conexa.technicalchallenge.controller.dto.people.PeopleDTO;
import com.conexa.technicalchallenge.controller.dto.people.PeopleResponseDTO;
import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.service.IPeopleService;
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
public class PeopleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPeopleService service;

    @MockBean
    private PeopleDTOConverter converter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAll() throws Exception {
        List<People> domainList = Collections.singletonList(new People());
        List<PeopleDTO> dtoList = Collections.singletonList(
                PeopleDTO.builder().uid("1").name("Luke Skywalker").build()
        );

        GenericContentPaginationWrapper<People> wrapper = GenericContentPaginationWrapper.<People>builder()
                .content(domainList)
                .build();

        when(service.getAll(PageRequest.of(0, 10))).thenReturn(wrapper);
        when(converter.wrapperToResponse(wrapper)).thenReturn(
                PeopleResponseDTO.builder().results(dtoList).build()
        );

        mockMvc.perform(get("/people?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].name").value("Luke Skywalker"));
    }

    @Test
    public void testGetById() throws Exception {
        int id = 1;
        People domain = new People();
        PeopleDTO peopleDTO = PeopleDTO.builder()
                .id("1")
                .name("Luke Skywalker")
                .build();

        when(service.getById(id)).thenReturn(domain);
        when(converter.domainToDTO(domain)).thenReturn(peopleDTO);

        mockMvc.perform(get("/people/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Luke Skywalker"));
    }

    @Test
    public void testGetByName() throws Exception {
        String name = "Luke Skywalker";
        List<People> domainList = Collections.singletonList(new People());
        List<PeopleDTO> dtoList = Collections.singletonList(
                PeopleDTO.builder()
                        .id("20")
                        .name("Storm IV Twin-Pod")
                        .build()
        );

        when(service.getByName(name)).thenReturn(domainList);
        when(converter.domainListToDTOList(domainList)).thenReturn(dtoList);

        mockMvc.perform(get("/people/name").param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Storm IV Twin-Pod"));
    }
}
