package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.mapper.CarMapper;
import com.damtab.my_bachelor_motors.dto.request.CarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.CarResponseDto;
import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_CAR)
public class CarController {

    private static final Logger log =
            LoggerFactory.getLogger(CarController.class);

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
        log.info("Requête GET terminée : {} voiture(s) récupé(s)");
        return ResponseEntity.ok(myListCar);
    }

    //Get Car By Id
    @GetMapping("{id}")
    public ResponseEntity<CarResponseDto> getCarById(@PathVariable long id) {
        Car myCar = carService.getCarById(id);
        log.info("Requête GET reçue : récupération du voiture ID={}", id);

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
        log.info("Requête POST terminée : voiture ID={}",
                savedCar.getCarId());
        return ResponseEntity.status(HttpStatus.CREATED).body(carMapper.toResponseDto(savedCar));
    }

    //Delete Car
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCarById(@PathVariable Long id) {
        boolean deleted = carService.deleteCar(id);
        if(!deleted) {
            return ResponseEntity.notFound().build();
        }
        log.info("Requête DELETE terminée : voiture ID={} supprimé", id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Update Car
    @PutMapping("{id}")
    public ResponseEntity<CarResponseDto> updatedCarById(@PathVariable Long id, @RequestBody CarRequestDto requestDto) {
        Car carToUpdate = carMapper.toEntity(requestDto);

        Car updatedCar = carService.updateCar(id, carToUpdate);
        log.info("Requête PUT terminée : voiture ID={} modifié", id);
        return ResponseEntity.ok(carMapper.toResponseDto(updatedCar));
    }
}
