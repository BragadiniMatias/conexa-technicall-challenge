package com.conexa.technicalchallenge.controller;

import com.conexa.technicalchallenge.controller.converter.VehicleDTOConverter;
import com.conexa.technicalchallenge.controller.dto.vehicle.VehicleResponseDTO;
import com.conexa.technicalchallenge.domain.Vehicle;
import com.conexa.technicalchallenge.service.IVehiclesService;
import com.conexa.technicalchallenge.service.impl.VehicleServiceImpl;
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
public class VehicleControllerTest {
    private final IVehiclesService service = mock(VehicleServiceImpl.class);
    private final VehicleDTOConverter converter = mock(VehicleDTOConverter.class);

    @InjectMocks
    private final VehicleController sut = new VehicleController(converter);

    private final Vehicle domain = mock(Vehicle.class);
    private final List<Vehicle> domainList = Collections.singletonList(domain);
    private final VehicleResponseDTO responseDTO = mock(VehicleResponseDTO.class);
    private final List<VehicleResponseDTO> responseDTOList =  Collections.singletonList(responseDTO);

    @Test
    void whenGetAll_thenCallServiceAndConverterAndReturnResultList(){
        when(service.getAll(any(Pageable.class))).thenReturn(domainList);
        when(converter.listDomainToDTOList(domainList)).thenReturn(responseDTOList);

        final ResponseEntity<List<VehicleResponseDTO>> result = sut.getAll(Pageable.unpaged());

        assertEquals(responseDTOList, result.getBody());
        verify(service, times(1)).getAll(any(Pageable.class));
        verify(converter, times(1)).listDomainToDTOList(domainList);
    }

    @Test
    void givenIdAndName_whenGetBy_thenCallServiceAndConverterAndReturnResult(){
        when(service.getById(VALID_ID)).thenReturn(domain);
        when(converter.domainToDTO(domain)).thenReturn(responseDTO);

        final ResponseEntity<VehicleResponseDTO> result = sut.getById(VALID_ID);

        assertEquals(responseDTO, result.getBody());
        verify(service, times(1)).getById(VALID_ID);
        verify(converter, times(1)).domainToDTO(domain);
    }

    @Test
    void givenNoData_whenGetBy_thenCallServiceAndConverterAndReturnResult(){
        when(service.getById(1)).thenReturn(domain);
        when(converter.domainToDTO(domain)).thenReturn(responseDTO);

        final ResponseEntity<VehicleResponseDTO> result = sut.getById(1);

        assertEquals(responseDTO, result.getBody());
        verify(service, times(1)).getById(1);
        verify(converter, times(1)).domainToDTO(domain);
    }
}
