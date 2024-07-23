package com.conexa.technicalchallenge.exceptions;

public class ExternalAPIServiceException extends RuntimeException{
    public ExternalAPIServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
