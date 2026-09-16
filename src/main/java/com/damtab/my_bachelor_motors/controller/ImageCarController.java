package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.entity.ImageCar;
import com.damtab.my_bachelor_motors.service.ImageCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_IMAGE_CAR)
public class ImageCarController {

    //Differents routing for...
    @Autowired
    private ImageCarService imageCarService;

    //Get List of Image-Car
    @GetMapping
    public ResponseEntity<Object> getAllImagesCar() {
        List<ImageCar> myListImageCar = imageCarService.getAllImageCars();
        if(myListImageCar == null || myListImageCar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(myListImageCar);
    }

    //Get Image-Car By Id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getImageCarById (@PathVariable Long id) {
        ImageCar myImageCar = imageCarService.getImageCarById(id);
        if(myImageCar == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(getImageCarById(id));
    }

    //Create Image-Car
    @PostMapping
    public ResponseEntity<Object> createImageCar (@RequestBody ImageCar myImageCar) {
        imageCarService.createImageCar(myImageCar);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //Delete Image-Car
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletImageCar (@PathVariable Long id) {
        Boolean toDelete = imageCarService.deleteImageCar(id);
        return toDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
