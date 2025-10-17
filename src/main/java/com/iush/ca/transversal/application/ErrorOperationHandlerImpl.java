package com.iush.ca.transversal.application;


import com.iush.ca.transversal.domain.models.exception.CAGenericException;
import com.iush.ca.transversal.domain.models.exception.ResourceNotFoundException;
import com.iush.ca.transversal.domain.ports.in.ErrorOperationHandler;
import com.iush.ca.transversal.domain.ports.out.DatabaseOperation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.QueryTimeoutException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ErrorOperationHandlerImpl implements ErrorOperationHandler {

    private final Class<? extends Exception>[] exceptionClasses = new Class[]{
            EntityNotFoundException.class,
            QueryTimeoutException.class,
            DataAccessException.class,
            DataIntegrityViolationException.class};

    @Override
    public String getNotFoundMessage(String component, int id) {
        String resourceNotFoundMessage = "The %s with id %s does not exist";
        return String.format(resourceNotFoundMessage, component, id);
    }

    @Override
    public String getNotFoundByNameMessage(String component, String name){
        String resourceNotFoundByNameMessage = "The %s with name %s does not exist";
        return String.format(resourceNotFoundByNameMessage, component, name);
    }



    @Override
    public <T> T handleDatabaseOperations(DatabaseOperation<T> operation,
                                          Class<? extends CAGenericException> dbExceptionClass,
                                          Class<? extends CAGenericException> genericExceptionClass,
                                          String operationDescription) throws CAGenericException {
       try {
           return operation.execute();
       } catch (ResourceNotFoundException ex) {
           throw ex;
       } catch (Exception ex) {
           if(isInstanceOf(ex)) {
               throw instantiateException(
                       dbExceptionClass,
                       getErrorMessage("Database error", operationDescription, ex), ex);
           } else {
               throw instantiateException(
                       genericExceptionClass,
                       getErrorMessage("Error", operationDescription, ex), ex);
           }
       }
    }

    private boolean isInstanceOf(Exception ex) {
        return Arrays.stream(exceptionClasses)
                .anyMatch(exceptionClass -> exceptionClass.isInstance(ex));
    }

    protected static <E extends  CAGenericException> E instantiateException(Class<E> exceptionClass, String message, Exception cause) {
        try {
            return exceptionClass.getConstructor(String.class, Throwable.class).newInstance(message, cause);
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating exception: " + exceptionClass.getName(), e);
        }
    }

    private static String getErrorMessage(String errorType, String operationDescription, Exception ex) {
        String errorMessage = "%s during %s. Error message: %s";
        return String.format(
                errorMessage,
                errorType,
                operationDescription,
                ex.getMessage());
    }
}
