package com.iush.ca.transversal.domain.models.exception;

import org.springframework.http.HttpStatus;

public class InstanceException  extends CAGenericException {
    public InstanceException(String message, Throwable cause) {
        super(message,
                cause,
                HttpStatus.INTERNAL_SERVER_ERROR,
                InstanceException.class.getName());
    }
}