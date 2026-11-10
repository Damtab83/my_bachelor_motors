package com.damtab.my_bachelor_motors.dto.response;

public record OldCarResponseDto(
        Long oldCarId,
        String brand,
        String model,
        int price
) {
}
