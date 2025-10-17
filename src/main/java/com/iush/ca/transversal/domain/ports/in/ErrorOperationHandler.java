package com.iush.ca.transversal.domain.ports.in;


import com.iush.ca.transversal.domain.models.exception.CAGenericException;
import com.iush.ca.transversal.domain.ports.out.DatabaseOperation;

public interface ErrorOperationHandler {
    String getNotFoundMessage(String component, int id);

    String getNotFoundByNameMessage(String component, String name);

    <T> T handleDatabaseOperations(DatabaseOperation<T> operation,
                                   Class<? extends CAGenericException> dbExceptionClass,
                                   Class<? extends CAGenericException> genericExceptionClass,
                                   String operationDescription) throws CAGenericException;
}