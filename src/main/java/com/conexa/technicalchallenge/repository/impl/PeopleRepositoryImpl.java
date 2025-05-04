package com.conexa.technicalchallenge.repository.impl;

import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.exceptions.ExternalAPIServiceException;
import com.conexa.technicalchallenge.exceptions.InvalidApiResponseException;
import com.conexa.technicalchallenge.repository.PeopleRepository;
import com.conexa.technicalchallenge.repository.converter.PeopleDAOConverter;

import com.conexa.technicalchallenge.repository.dao.people.*;
import com.conexa.technicalchallenge.utils.URLBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

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

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final PeopleDAOConverter converter;

    @Override
    public GenericContentPaginationWrapper getAll(final Pageable pageable) {
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

    @Override
    public List<People> getByName(String name) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = getPeopleByName(requestEntity, name);
        return getResponseDataOfByName(response.getBody());
    }

    private ResponseEntity<String> getAllPeopleApi(HttpEntity<Object> requestEntity, final Pageable pageable) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_PEOPLE_URL)
                    .queryParam("page", pageable.getPageNumber())
                    .queryParam("limit", pageable.getPageSize());
            return restTemplate.exchange(builder.toUriString(), GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception: {0}", exception.getMessage());
            throw new InvalidApiResponseException("Invalid data received from SWAPI while listing all people");
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Exception: {0}", exception.getMessage());
            throw new ExternalAPIServiceException("Unexpected error while retrieving all people", exception);
        }
    }

    private ResponseEntity<String> getPeopleById(HttpEntity<Object> requestEntity, final int id) {
        try {
            return restTemplate.exchange(BASE_PEOPLE_URL + "/" + id, GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new DataNotFoundException("People not found for ID: " + id);
            }
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception: {0}", exception.getMessage());
            throw new InvalidApiResponseException("Invalid data for people with ID: " + id);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Exception: {0}", exception.getMessage());
            throw new ExternalAPIServiceException("Unexpected error while retrieving people by ID", exception);
        }
    }


    private ResponseEntity<String> getPeopleByName(HttpEntity<Object> requestEntity, final String name) {
        try {
            return restTemplate.exchange(URLBuilder.buildUrlWithParam(BASE_PEOPLE_URL, NAME_QUERY_PARAM, name),HttpMethod.GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new DataNotFoundException("People not found for name: " + name);
            }
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception: {0}", exception.getMessage());
            throw new InvalidApiResponseException("Invalid data for people with name: " + name);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Exception: {0}", exception.getMessage());
            throw new ExternalAPIServiceException("Unexpected error while retrieving people by name", exception);
        }
    }

    private GenericContentPaginationWrapper getResponseDataOfGetAll(String response) {
        try {
            PeopleListResponse responseObj = objectMapper.readValue(response, PeopleListResponse.class);
            List<PeopleListItem> people = responseObj.getResults().stream()
                    .map(item -> {
                        PeopleListItem p = new PeopleListItem();
                        p.setUid(item.getUid());
                        p.setName(item.getName());
                        p.setUrl(item.getUrl());
                        return p;
                    })
                    .collect(Collectors.toList());

            return new GenericContentPaginationWrapper(
                    converter.peopleListItemToPeople(people),
                    responseObj.getTotalRecords(),
                    responseObj.getTotalPages()
            );
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", e.getMessage());
            throw new DataNotFoundException("Error processing JSON response for all people");
        }
    }

    private People getResponseDataOfById(String response) {
        try {
            PersonDetailResponse responseObj = objectMapper.readValue(response, PersonDetailResponse.class); //peopleResult
            return converter.personPropertiesToPeople(responseObj.getResult().getProperties());
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", e.getMessage());
            throw new DataNotFoundException("Error processing JSON response for people by ID");
        }
    }

    private List<People> getResponseDataOfByName(String response) {
        try {
            PersonSearchResponse responseObj = objectMapper.readValue(response, PersonSearchResponse.class);
            return responseObj.getResult().stream().map(a -> converter.personPropertiesToPeople(a.getProperties())).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", e.getMessage());
            throw new DataNotFoundException("Error processing JSON response for people by name");
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Mozilla/5.0");
        return headers;
    }
}
