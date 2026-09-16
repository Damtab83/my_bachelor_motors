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

    //Differents routing for ..
    @Autowired
    private CarService carService;

    //Get List of Cars
    @GetMapping
    public ResponseEntity<Object> getAllCars() {
        List<Car> myListCar = carService.getAllCars();
        if(myListCar == null || myListCar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(myListCar);
    }

    //Get Car By Id
    @GetMapping("{id}")
    public ResponseEntity<Object> getCarById(@PathVariable long id) {
        Car myCar = carService.getCarById(id);
        return myCar == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myCar);
    }

    //Create Car
    @PostMapping
    public ResponseEntity<Object> createCar(@RequestBody Car newCar) {
        carService.createCar(newCar);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    public ResponseEntity<Object> updatedCarById(@PathVariable Long id, @RequestBody Car newCar) {
        carService.updateCar(id, newCar);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
