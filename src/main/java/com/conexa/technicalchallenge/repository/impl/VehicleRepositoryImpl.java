package com.conexa.technicalchallenge.repository.impl;

import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.domain.Vehicle;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.exceptions.ExternalAPIServiceException;
import com.conexa.technicalchallenge.exceptions.InvalidApiResponseException;
import com.conexa.technicalchallenge.repository.VehicleRepository;
import com.conexa.technicalchallenge.repository.converter.VehicleDAOConverter;
import com.conexa.technicalchallenge.repository.dao.starship.StarshipSearchResponse;
import com.conexa.technicalchallenge.repository.dao.vehicle.VehicleDetailResponse;
import com.conexa.technicalchallenge.repository.dao.vehicle.VehicleListItem;
import com.conexa.technicalchallenge.repository.dao.vehicle.VehicleListResponse;
import com.conexa.technicalchallenge.repository.dao.vehicle.VehicleSearchResponse;
import com.conexa.technicalchallenge.utils.URLBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.conexa.technicalchallenge.utils.constants.UrlConstants.*;
import static org.springframework.http.HttpMethod.GET;

@AllArgsConstructor
@Repository
public class VehicleRepositoryImpl implements VehicleRepository {
    private static final Logger LOGGER = Logger.getLogger(VehicleRepositoryImpl.class.getName());

    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final VehicleDAOConverter converter;

    @Override
    public GenericContentPaginationWrapper<Vehicle> getAll(final Pageable pageable) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = getAllApiVehicle(requestEntity, pageable);
        return getResponseDataOfGetAll(response.getBody());
    }

    @Override
    public Vehicle getById(int id) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = getVehicleById(requestEntity, id);
        return getResponseDataOfById(response.getBody());
    }

    @Override
    public List<Vehicle> getByName(String name) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = getStarshipByName(requestEntity, name);
        return getResponseDataOfByName(response.getBody());
    }

    private ResponseEntity<String> getAllApiVehicle(HttpEntity<Object> requestEntity, final Pageable pageable){
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_VEHICLE_URL)
                    .queryParam("page", pageable.getPageNumber())
                    .queryParam("limit", pageable.getPageSize());
            return restTemplate.exchange(builder.toUriString(), GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception: {0}", exception.getMessage());
            throw new InvalidApiResponseException("Invalid data received from SWAPI while listing all vehicles. Code: " + exception.getStatusCode());
        } catch (Exception exception){
            LOGGER.log(Level.WARNING, "Exception: {0}", exception.getMessage());
            throw new ExternalAPIServiceException("An unexpected error ocurred while trying to retrieve all films", exception);
        }
    }

    private ResponseEntity<String> getVehicleById(HttpEntity<Object> requestEntity, int id){
        try{
            return restTemplate.exchange(BASE_VEHICLE_URL + "/" + id, GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new DataNotFoundException("Vehicle not found for requested id: " + id);
            }
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception: {0}", exception.getMessage());
            throw new InvalidApiResponseException("Invalid data received from SWAPI for film with id: " + id);
        } catch (Exception exception){
            LOGGER.log(Level.WARNING, "Exception: {0}", exception.getMessage());
            throw new ExternalAPIServiceException("An unexpected error ocurred while trying to retrieve vehicle by id", exception);
        }
    }

    private ResponseEntity<String> getStarshipByName(HttpEntity<Object> requestEntity, final String name) {
        try {
            return restTemplate.exchange(URLBuilder.buildUrlWithParam(BASE_VEHICLE_URL, NAME_QUERY_PARAM, name),HttpMethod.GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new DataNotFoundException("Vehicle not found for name: " + name);
            }
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception: {0}", exception.getMessage());
            throw new InvalidApiResponseException("Invalid data for vehicle with name: " + name);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Exception: {0}", exception.getMessage());
            throw new ExternalAPIServiceException("Unexpected error while retrieving vehicle by name", exception);
        }
    }

    private GenericContentPaginationWrapper<Vehicle> getResponseDataOfGetAll(String response) {
        try {
            VehicleListResponse responseDAO = objectMapper.readValue(response, VehicleListResponse.class);
            List<VehicleListItem> vehicleResult = responseDAO.getResults().stream().map(result -> {
                VehicleListItem vehicle = new VehicleListItem();
                vehicle.setUid(result.getUid());
                vehicle.setName(result.getName());
                vehicle.setUrl(result.getUrl());
                return vehicle;
            }).collect(Collectors.toList());
            return new GenericContentPaginationWrapper<Vehicle>(
                    converter.vehicleListItemToVehicleDomainList(vehicleResult),
                    responseDAO.getTotalRecords(),
                    responseDAO.getTotalPages()
            );
        } catch (JsonProcessingException exception) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", exception.getMessage());
            throw new DataNotFoundException("Error processing JSON response for all vehicles");
        }
    }


    private Vehicle getResponseDataOfById(String response) {
        try {
            VehicleDetailResponse responseObj = objectMapper.readValue(response, VehicleDetailResponse.class);
            return converter.vehiclePropertiestoVehicleDomain(responseObj.getResult().getProperties());
        } catch (JsonProcessingException exception) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", exception.getMessage());
            throw new DataNotFoundException("Error processing JSON response for vehicle by ID");
        }

    }


    private List<Vehicle> getResponseDataOfByName(String response) {
        try {
            VehicleSearchResponse responseObj = objectMapper.readValue(response, VehicleSearchResponse.class);
            return responseObj.getResult().stream().map(a -> converter.vehiclePropertiestoVehicleDomain(a.getProperties())).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", e.getMessage());
            throw new DataNotFoundException("Error processing JSON response for people by name");
        }
    }

    private HttpHeaders createHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Application");
        return headers;
    }
}
