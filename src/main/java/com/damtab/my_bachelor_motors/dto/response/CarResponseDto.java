package com.damtab.my_bachelor_motors.dto.response;

import java.util.List;

public record CarResponseDto(
        Long id,
        String brand,
        String model,
        String motorisation,
        int kilometer,
        int price,
        List<Long> images,
        TestDrivingResponseDto testDriving
) {
}
