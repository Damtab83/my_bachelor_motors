package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private static final Logger log =
            LoggerFactory.getLogger(CarService.class);

    //Create CRUD for Car
    private final CarRepository carRepository;


    //Read List of Cars
    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();

        log.debug("Nombre de voitures récupérées : {}", cars.size());

        return cars;
    }

    //Read Car by Id
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucune voiture trouvée avec l'identifiant : {}", id);
                    return new ResourceNotFoundException("Aucune voiture trouvée");
                });
    }

    //Create Car
    public Car createCar(Car car) {
         Car savedCar = carRepository.save(car);
         log.info("Voiture créée avec succès. Identifiant : {}", savedCar.getCarId());
         return savedCar;

    }

    //Delete Car
    public boolean deleteCar(Long id) {
        boolean toDelete = carRepository.existsById(id);
        if(toDelete) {
            carRepository.deleteById(id);
            log.info("Voiture supprimée avec succès. Identifiant : {}", id);
        }
        return toDelete;
    }

    //Update Car
    public Car updateCar(Long id, Car newCar) {
        Car oldCar = this.getCarById(id);
        if(oldCar != null) {
            oldCar.setBrand(newCar.getBrand());
            oldCar.setModel(newCar.getModel());
            oldCar.setMotorisation(newCar.getMotorisation());
            oldCar.setKilometer(newCar.getKilometer());
            oldCar.setPrice(newCar.getPrice());
            oldCar.setTestDriving(newCar.getTestDriving());
            oldCar.setImageCars(newCar.getImageCars());

            carRepository.save(oldCar);
            log.info("Voiture modifiée avec succès. Identifiant : {}", id);
        }
        return oldCar;
    }
}
