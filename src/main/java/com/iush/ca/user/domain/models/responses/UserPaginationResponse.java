package com.iush.ca.user.domain.models.responses;

import com.iush.ca.user.domain.models.entity.User;

public record UserPaginationResponse(
        String username,
        String name,
        String email,
        String role,
        String active
) {
    public UserPaginationResponse(User user) {
        this(
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getActive()
        );
    }
}

