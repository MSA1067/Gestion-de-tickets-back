package com.iush.ca.user.infrastructure.controllers;

import com.iush.ca.transversal.domain.models.entity.Selection;
import com.iush.ca.transversal.domain.models.exception.CAGenericException;
import com.iush.ca.transversal.domain.models.responses.PaginationResponse;
import com.iush.ca.transversal.domain.models.responses.ResponseList;
import com.iush.ca.transversal.domain.models.responses.ResponseObject;
import com.iush.ca.transversal.infrastructure.adapters.PaginationUtils;
import com.iush.ca.user.domain.models.dto.CreateUserDto;
import com.iush.ca.user.domain.models.dto.UpdateUserDto;
import com.iush.ca.user.domain.models.entity.User;
import com.iush.ca.user.domain.models.responses.UserPaginationResponse;
import com.iush.ca.user.domain.models.responses.UserResponse;
import com.iush.ca.user.domain.models.responses.UsersSummaryResponse;
import com.iush.ca.user.domain.ports.in.UserService;
import com.iush.ca.user.infrastructure.adapters.repository.UserQuerySpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.iush.ca.transversal.infrastructure.adapters.ResponseBuilder.buildResponse;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserQuerySpecification userQuerySpecification;

    @Value("${users.find.all.order.by}")
    private String orderBy;
    @Value("${users.find.all.url}")
    private String findAllUrl;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseList<UserPaginationResponse>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) List<String> roles
    ) throws CAGenericException {

        Page<User> usersPage = userService.getUsersFilters(
                PaginationUtils.buildPageable(page, size, orderBy),
                userQuerySpecification.userFilterSpecification(name, username, roles)
        );

        PaginationResponse pagination = PaginationUtils.buildPaginationResponse(
                usersPage, page, size, findAllUrl
        );

        return buildResponse(
                usersPage.stream().map(UserPaginationResponse::new).toList(),
                pagination
        );

    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPPORT')")
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

    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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
