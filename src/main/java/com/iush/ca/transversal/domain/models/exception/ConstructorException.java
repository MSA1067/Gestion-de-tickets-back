package com.iush.ca.transversal.domain.models.exception;

import org.springframework.http.HttpStatus;

public class ConstructorException extends CAGenericException {
    public ConstructorException(String message, Throwable cause) {
        super(
                message,
                cause,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ConstructorException.class.getName()
        );
    }
}
