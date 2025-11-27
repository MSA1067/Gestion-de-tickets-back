package com.iush.ca.user.infrastructure.adapters.repository.impl;

import com.iush.ca.transversal.domain.models.exception.CAGenericException;
import com.iush.ca.user.domain.models.entity.User;
import com.iush.ca.user.infrastructure.adapters.repository.UserAttributeSpecification;
import com.iush.ca.user.infrastructure.adapters.repository.UserQuerySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserQuerySpecificationImpl implements UserQuerySpecification {

    private final UserAttributeSpecification attributeSpecification;

    @Override
    public Specification<User> userFilterSpecification(String name,
                                                       String username,
                                                       List<String> rol) throws CAGenericException {

        return Specification.allOf(
                attributeSpecification.filterByNameInEntityUser(name),
                attributeSpecification.filterByUsernameInEntityUser(username),
                attributeSpecification.filterByRolInEntityUser(rol)
        );
    }
}
