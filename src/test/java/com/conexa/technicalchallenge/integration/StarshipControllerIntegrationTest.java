package com.conexa.technicalchallenge.integration;

import com.conexa.technicalchallenge.controller.converter.StarshipDTOConverter;

import com.conexa.technicalchallenge.controller.dto.starship.StarshipDTO;
import com.conexa.technicalchallenge.controller.dto.starship.StarshipResponseDTO;
import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.service.IStarshipService;
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
public class StarshipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStarshipService service;

    @MockBean
    private StarshipDTOConverter converter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAll() throws Exception {
        List<Starship> domainList = Collections.singletonList(new Starship());
        List<StarshipDTO> dtoList = Collections.singletonList(
                StarshipDTO.builder().uid("2").name("CR90 corvette").build()
        );

        GenericContentPaginationWrapper<Starship> wrapper = GenericContentPaginationWrapper.<Starship>builder()
                .content(domainList)
                .build();

        when(service.getAll(PageRequest.of(0, 10))).thenReturn(wrapper);
        when(converter.wrapperToResponse(wrapper)).thenReturn(
                StarshipResponseDTO.builder().results(dtoList).build()
        );

        mockMvc.perform(get("/starships?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].name").value("CR90 corvette"));
    }

    @Test
    public void testGetById() throws Exception {
        int id = 1;
        Starship domain = new Starship();
        StarshipDTO starshipDTO = StarshipDTO.builder()
                .id("2")
                .name("CR90 corvette")
                .build();

        when(service.getById(id)).thenReturn(domain);
        when(converter.domainToDTO(domain)).thenReturn(starshipDTO);

        mockMvc.perform(get("/starships/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("CR90 corvette"));
    }

    @Test
    public void testGetByName() throws Exception {
        String name = "CR90 corvette";
        List<Starship> domainList = Collections.singletonList(new Starship());
        List<StarshipDTO> dtoList = Collections.singletonList(
                StarshipDTO.builder()
                        .id("20")
                        .name("Storm IV Twin-Pod")
                        .build()
        );

        when(service.getByName(name)).thenReturn(domainList);
        when(converter.domainListToDTOList(domainList)).thenReturn(dtoList);

        mockMvc.perform(get("/starships/name").param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Storm IV Twin-Pod"));
    }
}
