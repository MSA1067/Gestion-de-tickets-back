package com.iush.ca.user.domain.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    @NotBlank(message = "The field username is required")
    private String username;

    @NotBlank(message = "The field password is required")
    private String password;

    @NotBlank(message = "The field name is required")
    private String name;

    private String role;
}
