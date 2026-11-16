package com.damtab.my_bachelor_motors.dto.mapper;

import com.damtab.my_bachelor_motors.dto.request.OldCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.OldCarResponseDto;
import com.damtab.my_bachelor_motors.entity.OldCar;
import org.springframework.stereotype.Component;

@Component
public class OldCarMapper {

    public OldCar toEntity(OldCarRequestDto dto) {
        OldCar oldCar = new OldCar();

        oldCar.setBrand(dto.brand());
        oldCar.setModel(dto.model());
        oldCar.setPrice(dto.price());

        return oldCar;
    }

    public OldCarResponseDto toResponseDto(OldCar oldCar) {
        return new OldCarResponseDto(
                oldCar.getOldCarId(),
                oldCar.getBrand(),
                oldCar.getModel(),
                oldCar.getPrice()
        );
    }
}
