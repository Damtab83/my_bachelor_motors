package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.entity.ImageCar;
import com.damtab.my_bachelor_motors.service.ImageCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_IMAGE_CAR)
public class ImageCarController {

    @Autowired
    private ImageCarService imageCarService;

    @GetMapping
    public ResponseEntity<Object> getAllImagesCar() {
        List<ImageCar> myListImageCar = imageCarService.getAllImageCars();
        return ResponseEntity.status(HttpStatus.OK).body(myListImageCar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getImageCarById (@PathVariable Long id) {
        Optional<ImageCar> myImageCar = imageCarService.getImageCarById(id);
        return myImageCar == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myImageCar);
    }

    @PostMapping
    public ResponseEntity<Object> createImageCar (@RequestBody ImageCar myImageCar) {
        imageCarService.createImageCar(myImageCar);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletImageCar (@PathVariable Long id) {
        Boolean toDelete = imageCarService.deleteImageCar(id);
        return toDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
