package com.iush.ca.transversal.domain.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("http_code_label")
    private HttpStatus httpCodeLabel;

    @JsonProperty("http_code")
    private int httpCode;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("detail")
    private List<AbstractMap.SimpleEntry<String, String>> detail;

    @JsonProperty("exception_name")
    private String exceptionName;

}