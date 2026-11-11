package com.damtab.my_bachelor_motors.dto.request;

public record ImageCarRequestDto(
        String name,
        Long size,
        String contentType,
        byte[] imageData
) {
}
