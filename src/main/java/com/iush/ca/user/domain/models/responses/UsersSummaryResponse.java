package com.iush.ca.user.domain.models.responses;

import com.iush.ca.user.domain.models.entity.User;

public record UsersSummaryResponse(

        String username,
        String name

) {

    public UsersSummaryResponse(User user){
        this(
                user.getUsername(),
                user.getName()
        );
    }
}
