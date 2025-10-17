package com.iush.ca.transversal.domain.models.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;

public class SelectionDeserializer extends JsonDeserializer<Selection> {


    @Override
    public Selection deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText().toUpperCase();

        if("YES".equals(value)){
            value = "S";
        }else if("NO".equals(value)){
            value = "N";
        }

        String finalValue = value;

        return Arrays.stream(Selection.values())
                .filter(s -> s.getValue().equals(finalValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid value for Selection: " + finalValue));
    }
}
