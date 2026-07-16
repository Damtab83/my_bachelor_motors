package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();
        if(cars.isEmpty()) {
            throw new ResourceNotFoundException("Aucunes voitures trouvées");
        }
        return cars;
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aucune voiture trouvée"));
    }

    public void createCar(Car car) {
        carRepository.save(car);
    }

    public boolean deleteCar(Long id) {
        Boolean toDelete = carRepository.existsById(id);
        if(toDelete) {
            carRepository.deleteById(id);
        }
        return toDelete;
    }

    public void updateCar(Long id, Car newCar) {
        Car oldCar = this.getCarById(id);
        if(oldCar != null) {
            oldCar.setBrand(newCar.getBrand());
            oldCar.setModel(newCar.getModel());
            oldCar.setMotorisation(newCar.getMotorisation());
            oldCar.setKilometer(newCar.getKilometer());
            oldCar.setPrice(newCar.getPrice());
            oldCar.setTestDriving(newCar.getTestDriving());
            oldCar.setImageCars(newCar.getImageCars());
        }
    }
}
