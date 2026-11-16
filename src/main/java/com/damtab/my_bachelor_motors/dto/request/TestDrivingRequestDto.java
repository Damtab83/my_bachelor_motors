package com.damtab.my_bachelor_motors.dto.request;

import java.time.LocalDateTime;

public record TestDrivingRequestDto(
        LocalDateTime testDate,
        Boolean confirmed
) {
}
