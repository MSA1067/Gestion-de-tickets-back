package com.iush.ca.transversal.domain.models.entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonDeserialize(using = SelectionDeserializer.class)
public enum Selection {

    YES("S"),
    NO("N");

    private final String value;

}
