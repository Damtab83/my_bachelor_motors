package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.mapper.ImageCarMapper;
import com.damtab.my_bachelor_motors.dto.request.ImageCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.ImageCarResponseDto;
import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.entity.ImageCar;
import com.damtab.my_bachelor_motors.service.ImageCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_IMAGE_CAR)
public class ImageCarController {

    //Differents routing for...
    @Autowired
    private ImageCarService imageCarService;
    @Autowired
    private ImageCarMapper imageCarMapper;

    public ImageCarController(ImageCarService imageCarService, ImageCarMapper imageCarMapper) {
        this.imageCarService = imageCarService;
        this.imageCarMapper = imageCarMapper;
    }

    //Get List of Image-Car
    @GetMapping
    public ResponseEntity<List<ImageCarResponseDto>> getAllImagesCar() {
        List<ImageCarResponseDto> myListImageCar = imageCarService.getAllImageCars()
                .stream()
                .map(imageCarMapper::toResponseDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(myListImageCar);
    }

    //Get Image-Car By Id
    @GetMapping("/{id}")
    public ResponseEntity<ImageCarResponseDto> getImageCarById (@PathVariable Long id) {
        ImageCar myImageCar = imageCarService.getImageCarById(id);
        return ResponseEntity.ok(imageCarMapper.toResponseDto(myImageCar));
    }

    //Create Image-Car
    @PostMapping
    public ResponseEntity<ImageCarResponseDto> createImageCar (@RequestBody ImageCarRequestDto requestDto) {
        ImageCar newImageCar = imageCarMapper.toEntity(requestDto);
        ImageCar savedImageCar = imageCarService.createImageCar(newImageCar);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageCarMapper.toResponseDto(savedImageCar));
    }

    @PostMapping(
            value = "/upload/{carId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ImageCar> uploadImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long carId
    ) throws IOException {

        ImageCar image = imageCarService.saveImage(file, carId);

        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }

    //Delete Image-Car
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletImageCar (@PathVariable Long id) {
        boolean toDelete = imageCarService.deleteImageCar(id);
        return toDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
