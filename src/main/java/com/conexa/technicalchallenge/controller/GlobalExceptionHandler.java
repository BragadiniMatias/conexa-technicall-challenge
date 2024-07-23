package com.conexa.technicalchallenge.controller;

import com.conexa.technicalchallenge.exceptions.DataNotFoundException;
import com.conexa.technicalchallenge.exceptions.ExternalAPIServiceException;
import com.conexa.technicalchallenge.exceptions.InvalidApiResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
//Maneja excepciones a nivel global. Responde con un ErrorResponse que es simplemente un objeto que contiene un mensaje a mostrar.
public class GlobalExceptionHandler {

    @ExceptionHandler(
            {
                    DataNotFoundException.class,
                    InvalidApiResponseException.class,
                    ExternalAPIServiceException.class
            }
    )
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
