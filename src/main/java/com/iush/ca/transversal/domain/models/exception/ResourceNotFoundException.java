package com.iush.ca.transversal.domain.models.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CAGenericException {
    public ResourceNotFoundException(String message) {
        super(message, null, HttpStatus.NOT_FOUND, ResourceNotFoundException.class.getName());

    }
}

