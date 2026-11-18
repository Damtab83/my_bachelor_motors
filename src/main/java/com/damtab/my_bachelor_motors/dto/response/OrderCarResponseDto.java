package com.damtab.my_bachelor_motors.dto.response;

import com.damtab.my_bachelor_motors.dto.LoginUserResponse;
import com.damtab.my_bachelor_motors.entity.OfferType;

public record OrderCarResponseDto(
        Long id,
        CarResponseDto car,
        Long userId,
        OfferType offerType,
        OldCarResponseDto oldCar,
        boolean subscriptionInsurance
) {
}
