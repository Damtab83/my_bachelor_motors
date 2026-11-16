package com.damtab.my_bachelor_motors.dto.response;

import org.hibernate.annotations.Bag;

import java.time.LocalDateTime;

public record TestDrivingResponseDto(
        Long testDrivingId,
        LocalDateTime testDate,
        Boolean confirmed
) {
}
