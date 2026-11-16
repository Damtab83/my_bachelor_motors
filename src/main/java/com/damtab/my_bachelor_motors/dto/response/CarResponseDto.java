package com.damtab.my_bachelor_motors.dto.response;

public record CarResponseDto(
        Long id,
        String brand,
        String model,
        String motorisation,
        int kilometer,
        int price
) {
}
