package com.iush.ca.transversal.domain.ports.out;

@FunctionalInterface
public interface DatabaseOperation<T> {
   T execute() throws Exception;
}
