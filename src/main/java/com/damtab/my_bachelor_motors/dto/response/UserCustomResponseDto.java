package com.damtab.my_bachelor_motors.dto.response;

import com.damtab.my_bachelor_motors.entity.Role;

import java.util.List;

public record UserCustomResponseDto(
        Long id,
        String firstname,
        String lastname,
        String email,
        Role role,
        List<OrderCarResponseDto> orderCars
) {
}
