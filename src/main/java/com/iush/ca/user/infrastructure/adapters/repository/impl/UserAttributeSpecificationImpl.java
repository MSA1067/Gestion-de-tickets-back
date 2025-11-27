package com.iush.ca.user.infrastructure.adapters.repository.impl;

import com.iush.ca.transversal.domain.models.exception.CAGenericException;
import com.iush.ca.transversal.infrastructure.repositories.AttributeSpecifications;
import com.iush.ca.user.domain.models.entity.User;
import com.iush.ca.user.infrastructure.adapters.repository.UserAttributeSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserAttributeSpecificationImpl implements UserAttributeSpecification {

    @Value("${user.filter.by.attribute.role}")
    private String userFilterByAttributeRole;
    @Value("${user.filter.by.attribute.name}")
    private String userFilterByAttributeName;
    @Value("${user.filter.by.attribute.username}")
    private String userFilterByAttributeUsername;


    @Override
    public Specification<User> filterByNameInEntityUser(String name) throws CAGenericException {
        return AttributeSpecifications.likeIgnoreCaseWithActive(
                Optional.ofNullable(name),
                userFilterByAttributeName
        );
    }

    @Override
    public Specification<User> filterByUsernameInEntityUser(String username) throws CAGenericException {
        return AttributeSpecifications.likeIgnoreCaseWithActive(
                Optional.ofNullable(username),
                userFilterByAttributeUsername
        );
    }

    @Override
    public Specification<User> filterByRolInEntityUser(List<String> roles) throws CAGenericException {
        return AttributeSpecifications.in(
                Optional.ofNullable(roles == null || roles.isEmpty() ? null : roles),
                userFilterByAttributeRole
        );
    }
}
