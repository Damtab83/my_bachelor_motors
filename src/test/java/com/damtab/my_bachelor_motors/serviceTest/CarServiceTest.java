package com.damtab.my_bachelor_motors.serviceTest;

import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.CarRepository;
import com.damtab.my_bachelor_motors.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car carTest;
    private Car carTest2;
    private List<Car> myCarList;

    @BeforeEach
    public void initializeCar() {

        carTest = new Car();
        carTest2 = new Car();
        myCarList = new ArrayList<>();

        carTest.setCarId(10L);
        carTest.setBrand("FIAT");
        carTest.setModel("X");
        carTest.setMotorisation("100CV");
        carTest.setKilometer(2000);
        carTest.setPrice(23000);

        carTest2.setCarId(20L);
        carTest2.setBrand("CITROEN");
        carTest2.setModel("Visa");
        carTest2.setMotorisation("60CV");
        carTest2.setKilometer(90000);
        carTest2.setPrice(2300);

        myCarList.add(carTest);
        myCarList.add(carTest2);
    }


    @Test
    public void getAllCar_shouldReturnList() throws Exception {

        Mockito.when(carRepository.findAll()).thenReturn(myCarList);

        List<Car> result = carService.getAllCars();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(10L, result.get(0).getCarId());
        assertEquals("FIAT", result.get(0).getBrand());
        assertEquals(20L, result.get(1).getCarId());
        assertEquals("CITROEN", result.get(1).getBrand());
    }

    @Test
    public void getCarById_shouldReturn_whenCarExist() throws Exception {

        Mockito.when(carRepository.findById(20L)).thenReturn(Optional.of(carTest2));

        Car result = carService.getCarById(20L);
        assertNotNull(result);
        assertEquals(20L, result.getCarId());
        assertEquals("60CV", result.getMotorisation());
        assertEquals(2300, result.getPrice());
    }

    @Test
    public void getCarById_shouldReturnFalse_whenCarNotExist() throws Exception {

        Mockito.when(carRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> carService.getCarById(99L)
        );

        assertEquals("Aucune voiture trouvée", exception.getMessage());
    }

    @Test
    public void createCar_shouldReturn201_whenCreated() throws Exception {
        carService.createCar(carTest);
        Mockito.verify(carRepository).save(carTest);
    }

    @Test
    public void deleteCarById_shouldDeleteCar_whenCarExist() throws Exception {

        Mockito.when(carRepository.existsById(10L)).thenReturn(true);
        Boolean result = carService.deleteCar(10L);
        assertTrue(result);
        Mockito.verify(carRepository).deleteById(10L);
    }

    @Test
    public void deleteCarById_shouldNotDeleteCar_whenCarNotExist() throws Exception {

        Mockito.when(carRepository.existsById(99L)).thenReturn(false);
        Boolean result = carService.deleteCar(99L);
        assertFalse(result);
        Mockito.verify(carRepository, Mockito.never()).deleteById(99L);
    }

    @Test
    public void updateCarById_shouldModifyCar_whenCarExist() throws Exception {

        Car newCar = new Car();
        newCar.setBrand("RENAULT");
        newCar.setModel("Zoé");
        newCar.setMotorisation("Electrique");
        newCar.setKilometer(12000);
        newCar.setPrice(8000);

        Mockito.when(carRepository.findById(20L)).thenReturn(Optional.of(carTest2));

        carService.updateCar(20L, newCar);

        assertEquals("RENAULT", carTest2.getBrand());
        assertEquals("Zoé", carTest2.getModel());
        assertEquals("Electrique", carTest2.getMotorisation());
        assertEquals(12000, carTest2.getKilometer());
        assertEquals(8000, carTest2.getPrice());

        Mockito.verify(carRepository).findById(20L);

    }
}
