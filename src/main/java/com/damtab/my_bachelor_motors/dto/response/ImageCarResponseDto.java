package com.damtab.my_bachelor_motors.dto.response;


public record ImageCarResponseDto(
        Long imageCarId,
        String name,
        Long size,
        String contentType,
        byte[] imageData
) {
}
