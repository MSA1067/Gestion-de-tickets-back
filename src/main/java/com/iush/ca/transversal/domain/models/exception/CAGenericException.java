package com.iush.ca.transversal.domain.models.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CAGenericException extends RuntimeException {

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("http_status")
    private HttpStatus httpStatus;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("exception_name")
    private String exceptionName;

    CAGenericException(String message, Throwable cause, HttpStatus httpStatus, String exceptionName) {
        super(message, cause);
        init(httpStatus, exceptionName);
    }

    public CAGenericException(String message, Throwable cause) {
        super(message, cause);
        init(HttpStatus.INTERNAL_SERVER_ERROR, this.getClass().getSimpleName());
    }

    public CAGenericException(String name) {
        super(name);
    }


    private void init(HttpStatus httpStatus, String exceptionName) {
        this.httpStatus = httpStatus;
        this.exceptionName = exceptionName;
        this.requestId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
    }

}

