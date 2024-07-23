package com.conexa.technicalchallenge.repository.impl;

import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.exceptions.ExternalAPIServiceException;
import com.conexa.technicalchallenge.exceptions.InvalidApiResponseException;
import com.conexa.technicalchallenge.repository.StarshipRepository;
import com.conexa.technicalchallenge.repository.converter.StarshipDAOConverter;
import com.conexa.technicalchallenge.repository.dao.starship.StarshipDAO;
import com.conexa.technicalchallenge.repository.dao.starship.StarshipResponseDAO;
import com.conexa.technicalchallenge.repository.dao.starship.StarshipsResponseDAO;
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
public class StarshipRepositoryImpl implements StarshipRepository {

    private static final Logger LOGGER = Logger.getLogger(FilmsRepositoryImpl.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final StarshipDAOConverter converter;

    @Override
    public List<Starship> getAll(final Pageable pageable) {
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

    private List<Starship> getResponseDataOfGetAll(String response) {
        try {
            StarshipsResponseDAO responseDAO = objectMapper.readValue(response, StarshipsResponseDAO.class);
            List<StarshipDAO> starshipResult = responseDAO.getResults().stream().map(result -> {
                StarshipDAO starship = new StarshipDAO();
                starship.setId(result.getId());
                starship.setName(result.getName());
                starship.setUrl(result.getUrl());
                return starship;
            }).collect(Collectors.toList());
            return converter.daoListToDomainList(starshipResult);
        } catch (JsonProcessingException exception) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", exception.getMessage());
            throw new DataNotFoundException("Error processing JSON response for all starships");
        }
    }


    private Starship getResponseDataOfById(String response) {
        try {
            StarshipResponseDAO responseDAO = objectMapper.readValue(response, StarshipResponseDAO.class);
            Starship starship = converter.daoToDomain(responseDAO.getResult().getProperties());
            starship.setId(responseDAO.getResult().getUid());
            return starship;
        } catch (JsonProcessingException exception) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", exception.getMessage());
            throw new DataNotFoundException("Error processing JSON response for starship by ID");
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
