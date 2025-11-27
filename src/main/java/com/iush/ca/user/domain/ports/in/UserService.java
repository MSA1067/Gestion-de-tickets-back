package com.iush.ca.user.domain.ports.in;

import com.iush.ca.transversal.domain.models.exception.CAGenericException;
import com.iush.ca.transversal.domain.models.exception.ResourceNotFoundException;
import com.iush.ca.user.domain.models.dto.UpdateUserDto;
import com.iush.ca.user.domain.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserService {

    List<User> getAllUserList() throws CAGenericException;

    Page<User> getUsersFilters(Pageable pageable, Specification<User> specification) throws CAGenericException;

    User getUserById(Integer id) throws ResourceNotFoundException;

    User createUser(User user) throws CAGenericException;

    User updateUser(Integer id, UpdateUserDto updateUserDto) throws CAGenericException;

}
