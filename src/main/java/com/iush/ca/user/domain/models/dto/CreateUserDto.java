package com.iush.ca.user.domain.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    @NotBlank(message = "The field username is required")
    private String username;

    @NotBlank(message = "The field password is required")
    private String password;

    @NotBlank(message = "The field email is required")
    private String email;

    @NotBlank(message = "The field name is required")
    private String name;

    @NotBlank(message = "The field role is required")
    private String role;

}
