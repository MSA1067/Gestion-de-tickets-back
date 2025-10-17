package com.iush.ca.user.infrastructure.controllers;

import com.iush.ca.transversal.domain.models.entity.Selection;
import com.iush.ca.transversal.domain.models.exception.CAGenericException;
import com.iush.ca.transversal.domain.models.exception.ResourceNotFoundException;
import com.iush.ca.transversal.domain.models.responses.ResponseList;
import com.iush.ca.transversal.domain.models.responses.ResponseObject;
import com.iush.ca.user.domain.models.dto.CreateUserDto;
import com.iush.ca.user.domain.models.dto.UpdateUserDto;
import com.iush.ca.user.domain.models.entity.User;
import com.iush.ca.user.domain.models.responses.UserResponse;
import com.iush.ca.user.domain.models.responses.UsersSummaryResponse;
import com.iush.ca.user.domain.ports.in.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.iush.ca.transversal.infrastructure.adapters.ResponseBuilder.buildResponse;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseList<UsersSummaryResponse>> getSummaryUsers() throws CAGenericException {
        List<User> users = userService.getAllUserList();
        return buildResponse(
                users.stream()
                        .map(UsersSummaryResponse::new)
                        .toList(),
                null
        );
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject<UserResponse>> getUserById(@PathVariable Integer id)
            throws CAGenericException {
        User user = userService.getUserById(id);
        return buildResponse(
                user,
                UserResponse.class,
                User.class,
                HttpStatus.OK
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject<UserResponse>> createUser(
            @Valid @RequestBody CreateUserDto createUserDto
    ) throws CAGenericException {

        User user = User.builder()
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .name(createUserDto.getName())
                .email(createUserDto.getEmail())
                .role(createUserDto.getRole())
                .active(Selection.YES.getValue())
                .build();

        User createdUser = userService.createUser(user);

        return buildResponse(
                createdUser,
                UserResponse.class,
                User.class,
                HttpStatus.CREATED
        );
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject<UserResponse>> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateUserDto updateUserDto
    ) throws CAGenericException {

        User updatedUser = userService.updateUser(id, updateUserDto);

        return buildResponse(
                updatedUser,
                UserResponse.class,
                User.class,
                HttpStatus.OK
        );
    }


}
