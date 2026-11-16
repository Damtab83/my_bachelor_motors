package com.damtab.my_bachelor_motors.dto.mapper;

import com.damtab.my_bachelor_motors.dto.request.CarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.CarResponseDto;
import com.damtab.my_bachelor_motors.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper  {

    public Car toEntity(CarRequestDto dto) {

        Car car = new Car();

        car.setBrand(dto.brand());
        car.setModel(dto.model());
        car.setMotorisation(dto.motorisation());
        car.setKilometer(dto.kilometer());
        car.setPrice(dto.price());

        return car;
    }

    public CarResponseDto toResponseDto(Car car) {
        return new CarResponseDto(
                car.getCarId(),
                car.getBrand(),
                car.getModel(),
                car.getMotorisation(),
                car.getKilometer(),
                car.getPrice()
        );
    }
}
