package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.mapper.CarMapper;
import com.damtab.my_bachelor_motors.dto.request.CarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.CarResponseDto;
import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_CAR)
public class CarController {

    //Differents routing for
    @Autowired
    private CarService carService;
    @Autowired
    private CarMapper carMapper;

    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }
    //Get List of Cars
    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        List<CarResponseDto> myListCar = carService.getAllCars()
                .stream()
                .map(carMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(myListCar);
    }

    //Get Car By Id
    @GetMapping("{id}")
    public ResponseEntity<CarResponseDto> getCarById(@PathVariable long id) {
        Car myCar = carService.getCarById(id);

        if (myCar == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carMapper.toResponseDto(myCar));

    }

    //Create Car
    @PostMapping
    public ResponseEntity<CarResponseDto> createCar(@RequestBody CarRequestDto requestDto) {
        Car newcar = carMapper.toEntity(requestDto);
        Car savedCar = carService.createCar(newcar);
        return ResponseEntity.status(HttpStatus.CREATED).body(carMapper.toResponseDto(savedCar));
    }

    //Delete Car
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCarById(@PathVariable Long id) {
        boolean deleted = carService.deleteCar(id);
        if(!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Update Car
    @PutMapping("{id}")
    public ResponseEntity<CarResponseDto> updatedCarById(@PathVariable Long id, @RequestBody CarRequestDto requestDto) {
        Car carToUpdate = carMapper.toEntity(requestDto);

        Car updatedCar = carService.updateCar(id, carToUpdate);

        return ResponseEntity.ok(carMapper.toResponseDto(updatedCar));
    }
}
