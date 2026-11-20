package com.damtab.my_bachelor_motors.dto.response;


import com.damtab.my_bachelor_motors.entity.OfferType;

public record OrderCarResponseDto(
        Long id,
        CarResponseDto carId,
        Long userId,
        OfferType offerType,
        OldCarResponseDto oldCarId,
        boolean subscriptionInsurance
) {
}
