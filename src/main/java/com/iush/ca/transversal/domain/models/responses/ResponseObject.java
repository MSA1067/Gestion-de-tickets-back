package com.iush.ca.transversal.domain.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ResponseObject<T> {
    @JsonProperty("data")
    private T data;
}
