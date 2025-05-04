package com.conexa.technicalchallenge.repository.impl;

import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.domain.helpers.wrappers.GenericContentPaginationWrapper;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.exceptions.ExternalAPIServiceException;
import com.conexa.technicalchallenge.exceptions.InvalidApiResponseException;
import com.conexa.technicalchallenge.repository.StarshipRepository;
import com.conexa.technicalchallenge.repository.converter.StarshipDAOConverter;
import com.conexa.technicalchallenge.repository.dao.starship.StarshipDetailResponse;
import com.conexa.technicalchallenge.repository.dao.starship.StarshipListItem;
import com.conexa.technicalchallenge.repository.dao.starship.StarshipListResponse;
import com.conexa.technicalchallenge.repository.dao.starship.StarshipSearchResponse;
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

@Repository
@AllArgsConstructor
public class StarshipRepositoryImpl implements StarshipRepository {

    private static final Logger LOGGER = Logger.getLogger(FIlmRepositoryImpl.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private final ObjectMapper objectMapper;

    private final StarshipDAOConverter converter;

    @Override
    public GenericContentPaginationWrapper<Starship> getAll(final Pageable pageable) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = getAllApiStarships(requestEntity, pageable);
        return getResponseDataOfGetAll(response.getBody());
    }

    @Override
    public Starship getById(int id) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = getStarshipsById(requestEntity, id);
        return getResponseDataOfById(response.getBody());
    }


    @Override
    public List<Starship> getByName(String name) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = getStarshipByName(requestEntity, name);
        return getResponseDataOfByName(response.getBody());
    }


    private ResponseEntity<String> getAllApiStarships(HttpEntity<Object> requestEntity, final Pageable pageable){
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_STARSHIPS_URL)
                    .queryParam("page", pageable.getPageNumber())
                    .queryParam("limit", pageable.getPageSize());
            return restTemplate.exchange(builder.toUriString(), GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception: {0}", exception.getMessage());
            throw new InvalidApiResponseException("Invalid data received from SWAPI while listing all starships. Code: " + exception.getStatusCode());
        } catch (Exception exception){
            LOGGER.log(Level.WARNING, "Exception: {0}", exception.getMessage());
            throw new ExternalAPIServiceException("An unexpected error ocurred while trying to retrieve all starships", exception);
        }
    }

    private ResponseEntity<String> getStarshipsById(HttpEntity<Object> requestEntity, int id){
        try{
            return restTemplate.exchange(BASE_STARSHIPS_URL + "/" + id, GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new DataNotFoundException("Starship not found for requested id: " + id);
            }
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception: {0}", exception.getMessage());
            throw new InvalidApiResponseException("Invalid data received from SWAPI for starship with id: " + id);
        } catch (Exception exception){
            LOGGER.log(Level.WARNING, "Exception: {0}", exception.getMessage());
            throw new ExternalAPIServiceException("An unexpected error ocurred while trying to retrieve starship by id", exception);
        }
    }

    private ResponseEntity<String> getStarshipByName(HttpEntity<Object> requestEntity, final String name) {
        try {
            return restTemplate.exchange(URLBuilder.buildUrlWithParam(BASE_STARSHIPS_URL, NAME_QUERY_PARAM, name),HttpMethod.GET, requestEntity, String.class);
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

    private GenericContentPaginationWrapper<Starship> getResponseDataOfGetAll(String response) {
        try {
            StarshipListResponse responseObj = objectMapper.readValue(response, StarshipListResponse.class);
            List<StarshipListItem> starships = responseObj.getResults().stream()
                    .map(item -> {
                        StarshipListItem s = new StarshipListItem();
                        s.setUid(item.getUid());
                        s.setName(item.getName());
                        s.setUrl(item.getUrl());
                        return s;
                    })
                    .collect(Collectors.toList());

            return new GenericContentPaginationWrapper<Starship>(
                    converter.starshipListItemToStarshipDomainList(starships),
                    responseObj.getTotalRecords(),
                    responseObj.getTotalPages()
            );
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", e.getMessage());
            throw new DataNotFoundException("Error processing JSON response for all people");
        }
    }


    private Starship getResponseDataOfById(String response) {
        try {
            StarshipDetailResponse responseDAO = objectMapper.readValue(response, StarshipDetailResponse.class);
            return converter.starshipPropertiestoStarshipDomain(responseDAO.getResult().getProperties());
        } catch (JsonProcessingException exception) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", exception.getMessage());
            throw new DataNotFoundException("Error processing JSON response for starship by ID");
        }
    }

    private List<Starship> getResponseDataOfByName(String response) {
        try {
            StarshipSearchResponse responseObj = objectMapper.readValue(response, StarshipSearchResponse.class);
            return responseObj.getResult().stream().map(a -> converter.starshipPropertiestoStarshipDomain(a.getProperties())).collect(Collectors.toList());
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
