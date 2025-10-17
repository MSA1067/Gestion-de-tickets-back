package com.iush.ca.transversal.domain.models.exception;

import org.springframework.http.HttpStatus;

public class CAException extends CAGenericException{
    public CAException(String message, Throwable cause) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR, CAException.class.getName());
    }
}
