package com.iush.ca.user.infrastructure.adapters.repository;

import com.iush.ca.transversal.domain.models.exception.CAGenericException;
import com.iush.ca.user.domain.models.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserAttributeSpecification {

    Specification<User> filterByNameInEntityUser (String name) throws CAGenericException;
    Specification<User> filterByUsernameInEntityUser (String username) throws CAGenericException;
    Specification<User> filterByRolInEntityUser (List<String> rol) throws CAGenericException;

}
