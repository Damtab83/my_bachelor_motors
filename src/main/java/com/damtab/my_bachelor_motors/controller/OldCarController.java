package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.mapper.OldCarMapper;
import com.damtab.my_bachelor_motors.dto.request.OldCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.OldCarResponseDto;
import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.entity.OldCar;
import com.damtab.my_bachelor_motors.service.OldCarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_OLD_CAR)
public class OldCarController {

    private static final Logger log =
            LoggerFactory.getLogger(OldCarController.class);

    //Differents routing for...
    @Autowired
    private OldCarService oldCarService;
    @Autowired
    private OldCarMapper oldCarMapper;

    public OldCarController(OldCarService oldCarService, OldCarMapper oldCarMapper) {
        this.oldCarService = oldCarService;
        this.oldCarMapper = oldCarMapper;
    }

    //Get List of Old-Car
    @GetMapping
    public ResponseEntity<List<OldCarResponseDto>> getAllOldCar() {
        List<OldCarResponseDto> myListOldCar = oldCarService.getAllOldCars()
                .stream()
                .map(oldCarMapper::toResponseDto)
                .toList();
        log.info("Requête GET terminée : {} ancienne(s) voiture(s) récupé(s)");
        return ResponseEntity.status(HttpStatus.OK).body(myListOldCar);
    }

    //Get Old-Car By Id
    @GetMapping("/{id}")
    public ResponseEntity<OldCarResponseDto> getOldCarById(@PathVariable Long id) {
        Optional<OldCar> oldCar = oldCarService.getOldCarById(id);
        log.info("Requête GET reçue : récupération d'ancienne voiture ID={}", id);

        if (oldCar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oldCarMapper.toResponseDto(oldCar.get()));
    }

    //Create Old-Car
    @PostMapping
    public ResponseEntity<OldCarResponseDto> createOldCar(@RequestBody OldCarRequestDto requestDto) {
        OldCar newOldCar = oldCarMapper.toEntity(requestDto);
        OldCar savedOldCar = oldCarService.createOldCar(newOldCar);
        log.info("Requête POST terminée : voiture ID={}",
                savedOldCar.getOldCarId());
        return ResponseEntity.status(HttpStatus.CREATED).body(oldCarMapper.toResponseDto(savedOldCar));
    }

    //Delete Old-Car
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOldCar(@PathVariable Long id) {
        boolean deleted = oldCarService.deleteOldCar(id);
        if(!deleted) {
            return ResponseEntity.notFound().build();
        }
        log.info("Requête DELETE terminée : d'ancienne voiture ID={} supprimé", id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
