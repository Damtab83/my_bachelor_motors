package com.damtab.my_bachelor_motors.dto.request;

public record CarRequestDto(
        String brand,
        String model,
        String motorisation,
        int kilometer,
        int price
) {
}
