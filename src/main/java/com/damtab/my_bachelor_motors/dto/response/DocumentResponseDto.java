package com.damtab.my_bachelor_motors.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;

public record DocumentResponseDto(
        Long id,
        String name,
        String mimeType,
        byte[] contenu
) {
}
