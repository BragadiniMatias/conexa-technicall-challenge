package com.conexa.technicalchallenge.repository.impl;

import com.conexa.technicalchallenge.domain.Film;
import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.exceptions.ExternalAPIServiceException;
import com.conexa.technicalchallenge.exceptions.InvalidApiResponseException;
import com.conexa.technicalchallenge.repository.FIlmRepository;
import com.conexa.technicalchallenge.repository.converter.FilmDAOConverter;
import com.conexa.technicalchallenge.repository.dao.film.FilmListResponse;
import com.conexa.technicalchallenge.repository.dao.film.FilmResponse;
import com.conexa.technicalchallenge.repository.dao.film.FilmResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
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

import static com.conexa.technicalchallenge.utils.constants.UrlConstants.BASE_FILMS_URL;
import static org.springframework.http.HttpMethod.GET;

@Repository
@AllArgsConstructor
public class FIlmRepositoryImpl implements FIlmRepository {

    private static final Logger LOGGER = Logger.getLogger(FIlmRepositoryImpl.class.getName());
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final FilmDAOConverter converter;

    @Override
    public List<Film> getAll() {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = getAllApiFilms(requestEntity);
        return getResponseDataOfGetAll(response.getBody());
    }

    @Override
    public Film getById(final int id) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = getApiFilmById(requestEntity, id);
        return getResponseDataOfById(response.getBody());

    }

    private ResponseEntity<String> getApiFilmById(HttpEntity<Object> requestEntity, final int id){
        try {
            return restTemplate.exchange(BASE_FILMS_URL + "/" + id, GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new DataNotFoundException("Film not found for requested id: " + id);
            }
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception for id {0}: {1}", new Object[]{id, exception.getMessage()});
            throw new InvalidApiResponseException("Invalid data received from SWAPI for film with id: " + id);
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Exception for id {0}: {1}", new Object[]{id, exception.getMessage()});
            throw new ExternalAPIServiceException("An unexpected error occurred while trying to retrieve film by id", exception);
        }
    }

    private ResponseEntity<String> getAllApiFilms(HttpEntity<Object> requestEntity) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_FILMS_URL);
            return restTemplate.exchange(builder.toUriString(), GET, requestEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            LOGGER.log(Level.WARNING, "HTTP Status Code Exception: {0}", exception.getMessage());
            throw new InvalidApiResponseException("Invalid data received from SWAPI while listing all films. Code: " + exception.getStatusCode());
        } catch (Exception exception){
            LOGGER.log(Level.WARNING, "Exception: {0}", exception.getMessage());
            throw new ExternalAPIServiceException("An unexpected error ocurred while trying to retrieve all films", exception);
        }
    }

    private Film getResponseDataOfById(String response) {
        try {
            FilmResponse filmResponse = objectMapper.readValue(response, FilmResponse.class);
            return converter.propertiesDaoToDomain(filmResponse.getResult().getProperties());
        } catch (JsonProcessingException exception) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", exception.getMessage());
            throw new DataNotFoundException("Error processing JSON response for film by ID");
        }

}

    private List<Film> getResponseDataOfGetAll(String response) {
        try {
            FilmListResponse filmListResponse = objectMapper.readValue(response, FilmListResponse.class);
            return converter.filmPropertiesToDomainList(filmListResponse.getResult().stream().map(FilmResult::getProperties).collect(Collectors.toList()));
        } catch (JsonProcessingException exception) {
            LOGGER.log(Level.WARNING, "JSON Processing Exception: {0}", exception.getMessage());
            throw new DataNotFoundException("Error processing JSON response for all films");
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
