package com.conexa.technicalchallenge.controller;

import com.conexa.technicalchallenge.controller.converter.VehicleDTOConverter;
import com.conexa.technicalchallenge.controller.dto.vehicle.VehicleDTO;
import com.conexa.technicalchallenge.controller.dto.vehicle.VehicleResponseDTO;
import com.conexa.technicalchallenge.domain.Vehicle;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.service.IVehiclesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
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
public class VehicleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IVehiclesService service;

    @MockBean
    private VehicleDTOConverter converter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAll() throws Exception {
        List<Vehicle> domainList = Collections.singletonList(new Vehicle());
        List<VehicleDTO> dtoList = Collections.singletonList(
                VehicleDTO.builder().uid("20").name("Storm IV Twin-Pod").build()
        );

        GenericContentPaginationWrapper<Vehicle> wrapper = GenericContentPaginationWrapper.<Vehicle>builder()
                .content(domainList)
                .build();

        when(service.getAll(PageRequest.of(0, 10))).thenReturn(wrapper);
        when(converter.wrapperToResponse(wrapper)).thenReturn(
                VehicleResponseDTO.builder().results(dtoList).build()
        );

        mockMvc.perform(get("/vehicles?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].name").value("Storm IV Twin-Pod"));
    }

    @Test
    public void testGetById() throws Exception {
        int id = 1;
        Vehicle domain = new Vehicle();
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .id("20")
                .name("Storm IV Twin-Pod")
                .build();

        when(service.getById(id)).thenReturn(domain);
        when(converter.domainToDTO(domain)).thenReturn(vehicleDTO);

        mockMvc.perform(get("/vehicles/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Storm IV Twin-Pod"));
    }

    @Test
    public void testGetByName() throws Exception {
        String name = "Speeder";
        List<Vehicle> domainList = Collections.singletonList(new Vehicle());
        List<VehicleDTO> dtoList = Collections.singletonList(
                VehicleDTO.builder()
                        .id("20")
                        .name("Storm IV Twin-Pod")
                        .build()
        );

        when(service.getByName(name)).thenReturn(domainList);
        when(converter.listDomainToDTOList(domainList)).thenReturn(dtoList);

        mockMvc.perform(get("/vehicles/name").param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Storm IV Twin-Pod"));
    }
}
