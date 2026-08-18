package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.entity.OldCar;
import com.damtab.my_bachelor_motors.service.OldCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_OLD_CAR)
public class OldCarController {

    @Autowired
    private OldCarService oldCarService;

    @GetMapping
    public ResponseEntity<Object> getAllOldCar() {
        List<OldCar> myListOldCar = oldCarService.getAllOldCars();
        return ResponseEntity.status(HttpStatus.OK).body(myListOldCar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOldCarById(@PathVariable Long id) {
        Optional<OldCar> oldCar = oldCarService.getOldCarById(id);
        return oldCar == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(oldCar);
    }

    @PostMapping
    public ResponseEntity<Object> createOldCar(@RequestBody OldCar newOldCar) {
        oldCarService.createOldCar(newOldCar);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOldCar(@PathVariable Long id) {
        oldCarService.deleteOldCar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
