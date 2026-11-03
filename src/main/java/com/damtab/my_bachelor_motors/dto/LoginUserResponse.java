package com.damtab.my_bachelor_motors.dto;

public record LoginUserResponse(
        Long id,
        String firstname,
        String lastname,
        String email,
        String role
) {
}
