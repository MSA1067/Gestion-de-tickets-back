package com.iush.ca.transversal.domain.models.exception;

import org.springframework.http.HttpStatus;

public class CADbException extends CAGenericException{
    public CADbException(String message, Throwable cause) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR, CADbException.class.getName());
    }
}