package com.damtab.my_bachelor_motors.dto.request;

import com.damtab.my_bachelor_motors.entity.OfferType;

public record OrderCarRequestDto(
        Long carId,
        Long userId,
        OfferType offerType,
        Long oldCarId,
        boolean subscriptionInsurance
) {
}
