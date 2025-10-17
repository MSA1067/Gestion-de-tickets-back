package com.iush.ca.transversal.infrastructure.config;

import com.iush.ca.transversal.domain.models.dto.ErrorResponse;
import com.iush.ca.transversal.domain.models.exception.CAGenericException;
import com.iush.ca.transversal.domain.models.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handlerValidationExceptions(MethodArgumentNotValidException ex) {
        List<AbstractMap.SimpleEntry<String, String>> errors = processValidationError(ex.getBindingResult());
        return new ResponseEntity<>(buildErrorResponse(ex, HttpStatus.BAD_REQUEST, errors), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({CAGenericException.class})
    public ResponseEntity<ErrorResponse> handlerPortfolioException(CAGenericException ex) {
        return new ResponseEntity<>(buildErrorResponse(ex), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> unhandledError(Exception ex) {
        return new ResponseEntity<>(buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR,null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                buildErrorResponse(ex, HttpStatus.NOT_FOUND, null),
                HttpStatus.NOT_FOUND
        );
    }

    private ErrorResponse buildErrorResponse(CAGenericException ex) {
        return new ErrorResponse(ex.getTimestamp(),
                ex.getHttpStatus(),
                ex.getHttpStatus().value(),
                ex.getRequestId(),
                ex.getMessage(),
                null,
                ex.getExceptionName());
    }

    private ErrorResponse buildErrorResponse(Exception exception, HttpStatus httpStatus, List<SimpleEntry<String, String>> details) {
        return new ErrorResponse(LocalDateTime.now(),
                httpStatus,
                httpStatus.value(),
                UUID.randomUUID().toString(),
                exception.getMessage(),
                details,
                exception.getClass().getName()
        );
    }

    private List<SimpleEntry<String, String>> processValidationError(BindingResult bindingResult) {
        return Optional.of(
                bindingResult.getFieldErrors()
                        .stream()
                        .map(error -> new SimpleEntry<>(
                                error.getField(),
                                error.getDefaultMessage() != null ? error.getDefaultMessage() : "Unknown error"
                        ))
                        .collect(Collectors.toList())
        ).orElseGet(List::of);
    }
}