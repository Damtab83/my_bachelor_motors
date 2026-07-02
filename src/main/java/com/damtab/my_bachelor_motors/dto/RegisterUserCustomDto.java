package com.damtab.my_bachelor_motors.dto;


import com.damtab.my_bachelor_motors.entity.UserCustom;

public record RegisterUserCustomDto(
        String firstname,
        String lastname,
        String email,
        String password
) {
}
