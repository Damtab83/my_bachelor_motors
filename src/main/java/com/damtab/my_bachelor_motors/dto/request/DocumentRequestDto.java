package com.damtab.my_bachelor_motors.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;

public record DocumentRequestDto(
        String name,
        String mimeType,
        byte[] contenu
) {
}
