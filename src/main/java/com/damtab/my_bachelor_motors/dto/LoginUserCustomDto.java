package com.damtab.my_bachelor_motors.dto;

public record LoginUserCustomDto(

        Long id,
        String firstname,
        String lastname,
        String email,
        String password,
        String role
) {}
