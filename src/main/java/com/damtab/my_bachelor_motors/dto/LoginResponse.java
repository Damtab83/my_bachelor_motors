package com.damtab.my_bachelor_motors.dto;

public record LoginResponse(
        String token,
        LoginUserResponse userdto
) {
}
