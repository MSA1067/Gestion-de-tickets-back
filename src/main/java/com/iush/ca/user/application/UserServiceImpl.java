package com.iush.ca.user.application;

import com.iush.ca.transversal.domain.models.entity.Selection;
import com.iush.ca.transversal.domain.models.exception.CADbException;
import com.iush.ca.transversal.domain.models.exception.CAException;
import com.iush.ca.transversal.domain.models.exception.CAGenericException;
import com.iush.ca.transversal.domain.models.exception.ResourceNotFoundException;
import com.iush.ca.transversal.domain.ports.in.ErrorOperationHandler;
import com.iush.ca.user.domain.models.dto.UpdateUserDto;
import com.iush.ca.user.domain.models.entity.User;
import com.iush.ca.user.domain.ports.in.UserService;
import com.iush.ca.user.domain.ports.out.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ErrorOperationHandler errorOperationHandler;
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUserList() throws CAGenericException {
        return errorOperationHandler.handleDatabaseOperations(
                userRepository::findAll,
                CADbException.class,
                CAException.class,
                "Find all users"
        );
    }

    @Override
    public User getUserById(Integer id) throws ResourceNotFoundException {
        return errorOperationHandler.handleDatabaseOperations(
                () -> userRepository.findByIdAndActive(id , Selection.YES.getValue())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                errorOperationHandler.getNotFoundMessage("User " , id)
                        )),
                CADbException.class,
                CAException.class,
                "Find user by id"
        );
    }

    @Override
    public User createUser(User user) throws CAGenericException {
        return errorOperationHandler.handleDatabaseOperations(
                () -> userRepository.save(user),
                CADbException.class,
                CAException.class,
                "Create user"
        );
    }

    @Override
    public User updateUser(Integer id, UpdateUserDto updateUserDto) throws CAGenericException {
        return errorOperationHandler.handleDatabaseOperations(() -> {
                    User existingUser = getUserById(id);

                    existingUser.setUsername(updateUserDto.getUsername());
                    existingUser.setPassword(updateUserDto.getPassword());
                    existingUser.setName(updateUserDto.getName());
                    existingUser.setRole(updateUserDto.getRole());

                    return userRepository.save(existingUser);
                },
                CADbException.class,
                CAException.class,
                "Update user");
    }



}
