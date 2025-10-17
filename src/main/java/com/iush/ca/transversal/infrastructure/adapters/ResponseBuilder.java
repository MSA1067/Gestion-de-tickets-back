package com.iush.ca.transversal.infrastructure.adapters;

import com.iush.ca.transversal.domain.models.exception.CAGenericException;
import com.iush.ca.transversal.domain.models.exception.ConstructorException;
import com.iush.ca.transversal.domain.models.exception.InstanceException;
import com.iush.ca.transversal.domain.models.responses.PaginationResponse;
import com.iush.ca.transversal.domain.models.responses.ResponseList;
import com.iush.ca.transversal.domain.models.responses.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ResponseBuilder {

    private static <T, E> ResponseObject<T> buildObjectResponse(E data, Class<T> clazz, Class<E> clazzData) throws CAGenericException {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(clazzData);
            T instance = constructor.newInstance(data);
            return new ResponseObject<T>(instance);
        } catch (NoSuchMethodException e) {
            throw new ConstructorException(e.getMessage(), e.getCause());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new InstanceException(e.getMessage(), e.getCause());
        }
    }

    private static <T> ResponseList<T> buildListResponse(List<T> data, PaginationResponse pagination) throws CAGenericException {
        return new ResponseList<>(data, pagination);
    }

    public static <T,E> ResponseEntity<ResponseObject<T>> buildResponse(E data, Class<T> clazz, Class<E> clazzData, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(buildObjectResponse(data, clazz, clazzData));
    }

    public static <T> ResponseEntity<ResponseList<T>> buildResponse(List<T> data, PaginationResponse pagination) {
        return ResponseEntity.ok(
                buildListResponse(data, pagination)
        );
    }

    public static <T> ResponseEntity<ResponseObject<T>> buildBasicResponse(T data, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(new ResponseObject<>(data));
    }


}
