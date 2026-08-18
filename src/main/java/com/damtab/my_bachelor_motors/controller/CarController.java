package com.damtab.my_bachelor_motors.controller;

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

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<Object> getAllCars() {
        List<Car> myListCar = carService.getAllCars();
        return ResponseEntity.status(HttpStatus.OK).body(myListCar);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getCarById(@PathVariable long id) {
        Car myCar = carService.getCarById(id);
        return myCar == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myCar);
    }

    @PostMapping
    public ResponseEntity<Object> createCar(@RequestBody Car newCar) {
        carService.createCar(newCar);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCarById(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updatedCarById(@PathVariable Long id, @RequestBody Car newCar) {
        carService.updateCar(id, newCar);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
