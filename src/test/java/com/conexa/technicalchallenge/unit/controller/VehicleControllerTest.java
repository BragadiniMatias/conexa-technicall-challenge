package com.conexa.technicalchallenge.unit.controller;

import com.conexa.technicalchallenge.controller.VehicleController;
import com.conexa.technicalchallenge.controller.converter.VehicleDTOConverter;
import com.conexa.technicalchallenge.controller.dto.vehicle.VehicleDTO;
import com.conexa.technicalchallenge.controller.dto.vehicle.VehicleResponseDTO;
import com.conexa.technicalchallenge.domain.Vehicle;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.service.IVehiclesService;
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
public class VehicleControllerTest {
    @Mock
    private IVehiclesService service;

    @Mock
    private VehicleDTOConverter converter;

    @InjectMocks
    private VehicleController controller;


    @Test
    void getById_returnsVehicleDTO_whenValidIdProvided() {
        int id = 20;
        Vehicle mockDomain = new Vehicle();
        VehicleDTO dto = VehicleDTO.builder().uid("20").name("Storm IV Twin-Pod").build();

        when(service.getById(id)).thenReturn(mockDomain);
        when(converter.domainToDTO(mockDomain)).thenReturn(dto);

        ResponseEntity<VehicleDTO> response = controller.getById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getByName_returnsListOfVehicleDTO_whenNameExists() {
        String name = "Storm";
        List<Vehicle> mockDomainList = Collections.singletonList(new Vehicle());
        List<VehicleDTO> dtoList = Collections.singletonList(VehicleDTO.builder().name("Storm IV Twin-Pod").build());

        when(service.getByName(name)).thenReturn(mockDomainList);
        when(converter.listDomainToDTOList(mockDomainList)).thenReturn(dtoList);

        ResponseEntity<List<VehicleDTO>> response = controller.getByName(name);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dtoList, response.getBody());
    }

    @Test
    void getAllVehicles_returnsListOfVehicles_whenPageableProvided() {
        Pageable pageable = Pageable.unpaged();
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .id("20")
                .name("Storm IV Twin-Pod")
                .build();

        Vehicle vehicle = Vehicle.builder()
                .id("20")
                .name("Storm IV Twin-Pod")
                .build();

        GenericContentPaginationWrapper<Vehicle> wrapper = GenericContentPaginationWrapper.<Vehicle>builder()
                .content(Collections.singletonList(vehicle))
                .build();

        VehicleResponseDTO response = VehicleResponseDTO.builder()
                .results(Collections.singletonList(vehicleDTO))
                .build();

        when(service.getAll(pageable)).thenReturn(wrapper);
        when(converter.wrapperToResponse(wrapper)).thenReturn(response);

        ResponseEntity<VehicleResponseDTO> controllerResp = controller.getAll(pageable);

        assertEquals(200, controllerResp.getStatusCodeValue());
        assertEquals(1, controllerResp.getBody().getResults().size());

    }


    @Test
    void getById_returnsNotFoundException_whenVehicleNotFoundById(){
        int invalidId = 999999;

        when(service.getById(invalidId)).thenThrow(new DataNotFoundException("Vehicle not found for given id"));

        try {
            controller.getById(invalidId);
        } catch (DataNotFoundException ex) {
            assertEquals("Vehicle not found for given id", ex.getMessage());
        }

        verify(service).getById(invalidId);
        //Nno se deberia invocar nunca porque no tiene que pasar x el converter en este punto
        verifyNoInteractions(converter);


    }


}
