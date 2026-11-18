package com.damtab.my_bachelor_motors.dto.request;

import com.damtab.my_bachelor_motors.entity.Role;

public record UserCustomRequestDto(
        String firstname,
        String lastname,
        String email,
        Role role
) {
}
