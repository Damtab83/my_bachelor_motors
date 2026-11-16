package com.damtab.my_bachelor_motors.dto.response;

import com.damtab.my_bachelor_motors.entity.OfferType;

public record OrderCarResponseDto(
        Long id,
        Long carId,
        Long userId,
        OfferType offerType,
        Long oldCarId,
        boolean subscriptionInsurance
) {
}
