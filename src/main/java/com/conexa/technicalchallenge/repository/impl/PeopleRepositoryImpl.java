package com.conexa.technicalchallenge.repository.impl;

import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.exceptions.ExternalAPIServiceException;
import com.conexa.technicalchallenge.exceptions.InvalidApiResponseException;
import com.conexa.technicalchallenge.repository.PeopleRepository;
import com.conexa.technicalchallenge.repository.converter.PeopleDAOConverter;
import com.conexa.technicalchallenge.repository.dao.people.PeopleDAO;
import com.conexa.technicalchallenge.repository.dao.people.PeopleResponseDAO;
import com.conexa.technicalchallenge.repository.dao.people.PeoplesResponseDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.conexa.technicalchallenge.utils.constants.UrlConstants.*;
import static org.springframework.http.HttpMethod.GET;

@Repository
@AllArgsConstructor
public class PeopleRepositoryImpl implements PeopleRepository {
    private static final Logger LOGGER = Logger.getLogger(PeopleRepositoryImpl.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final PeopleDAOConverter converter;

    @Override
    public List<People> getAll(final Pageable pageable) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = getAllPeopleApi(requestEntity, pageable);
        return getResponseDataOfGetAll(response.getBody());
    }

    @Override
    public People getById(int id) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = getPeopleById(requestEntity, id);
        return getResponseDataOfById(response.getBody());
    }


    private ResponseEntity<String> getAllPeopleApi(HttpEntity<Object> requestEntity, final Pageable pageable){
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_PEOPLE_URL)
                    .queryParam("page", pageable.getPageNumber())
                    .queryParam("limit", pageable.getPageSize());
            return restTemplate.exchange(builder.toUriString(), GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception: {0}", exception.getMessage());
            throw new InvalidApiResponseException("Invalid data received from SWAPI while listing all people");
        } catch (Exception exception){
            LOGGER.log(Level.WARNING, "Exception: {0}", exception.getMessage());
            throw new ExternalAPIServiceException("An unexpected error ocurred while trying to retrieve all people", exception);
        }
    }

    private ResponseEntity<String> getPeopleById(HttpEntity<Object> requestEntity, int id){
        try{
            return restTemplate.exchange(BASE_PEOPLE_URL + "/" + id, GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new DataNotFoundException("People not found for requested id: " + id);
            }
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception: {0}", exception.getMessage());
            throw new InvalidApiResponseException("Invalid data received from SWAPI for people with id: " + id);
        } catch (Exception exception){
            LOGGER.log(Level.WARNING, "Exception: {0}", exception.getMessage());
            throw new ExternalAPIServiceException("An unexpected error ocurred while trying to retrieve people by id", exception);
        }
    }

    private List<People> getResponseDataOfGetAll(String response) {
        try {
            PeoplesResponseDAO responseDAO = objectMapper.readValue(response, PeoplesResponseDAO.class);
            List<PeopleDAO> peoplesDAOList = responseDAO.getResults().stream().map(result -> {
                PeopleDAO starship = new PeopleDAO();
                starship.setId(result.getId());
                starship.setName(result.getName());
                starship.setUrl(result.getUrl());
                return starship;
            }).collect(Collectors.toList());
            return converter.daoListToDomainList(peoplesDAOList);
        } catch (JsonProcessingException exception) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", exception.getMessage());
            throw new DataNotFoundException("Error processing JSON response for all people");
        }
    }


    private People getResponseDataOfById(String response) {
        try {
            PeopleResponseDAO responseDAO = objectMapper.readValue(response, PeopleResponseDAO.class);
            People people = converter.daoToDomain(responseDAO.getResult().getProperties());
            people.setId(responseDAO.getResult().getUid());
            return people;
        } catch (JsonProcessingException exception) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", exception.getMessage());
            throw new DataNotFoundException("Error processing JSON response for people by ID");
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
