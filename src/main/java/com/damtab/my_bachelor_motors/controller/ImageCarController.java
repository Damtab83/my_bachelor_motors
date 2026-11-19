package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.mapper.ImageCarMapper;
import com.damtab.my_bachelor_motors.dto.request.ImageCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.ImageCarResponseDto;
import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.entity.ImageCar;
import com.damtab.my_bachelor_motors.service.ImageCarService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequiredArgsConstructor
public class ImageCarController {

    @PostConstruct
    public void init() {
        log.info("==================================");
        log.info("IMAGE CAR CONTROLLER INITIALIZED");
        log.info("==================================");
    }




    private static final Logger log =
            LoggerFactory.getLogger(ImageCarController.class);

    //Differents routing for...
    private final ImageCarService imageCarService;
    private final ImageCarMapper imageCarMapper;


    //Get List of Image-Car
    @GetMapping
    public ResponseEntity<List<ImageCarResponseDto>> getAllImagesCar() {
        List<ImageCarResponseDto> myListImageCar = imageCarService.getAllImageCars()
                .stream()
                .map(imageCarMapper::toResponseDto)
                .toList();
        log.info("Requête GET terminée : {} image(s) récupée(s)");
        return ResponseEntity.status(HttpStatus.OK).body(myListImageCar);
    }

    //Get Image-Car By Id
    @GetMapping("/{id}")
    public ResponseEntity<ImageCarResponseDto> getImageCarById (@PathVariable Long id) {
        ImageCar myImageCar = imageCarService.getImageCarById(id);
        log.info("Requête GET reçue : récupération de l'image ID={}", id);
        return ResponseEntity.ok(imageCarMapper.toResponseDto(myImageCar));
    }

    //Create Image-Car
    @PostMapping
    public ResponseEntity<ImageCarResponseDto> createImageCar (@RequestBody ImageCarRequestDto requestDto) {
        ImageCar newImageCar = imageCarMapper.toEntity(requestDto);
        ImageCar savedImageCar = imageCarService.createImageCar(newImageCar);
        log.info("Requête POST terminée : véhicule ID={}",
                savedImageCar.getImageCarId());
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

        log.info("========== UPLOAD IMAGE ==========");
        log.info("Car ID : {}", carId);
        log.info("Nom du fichier : {}", file.getOriginalFilename());
        log.info("Taille : {}", file.getSize());

        ImageCar image = imageCarService.saveImage(file, carId);
        log.info("Upload de l'image terminé");

        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }

    //Delete Image-Car
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletImageCar (@PathVariable Long id) {
        boolean toDelete = imageCarService.deleteImageCar(id);
        log.info("Requête DELETE terminée : image ID={} supprimée", id);
        return toDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
