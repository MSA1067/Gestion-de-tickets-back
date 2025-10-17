package com.iush.ca.user.domain.models.responses;

import com.iush.ca.user.domain.models.entity.User;

public record UserResponse(

        String username,
        String name,
        String email,
        String role

) {

    public UserResponse (User user) {
        this(
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

}
