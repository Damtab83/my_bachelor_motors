package com.damtab.my_bachelor_motors.controllerTest;


import com.damtab.my_bachelor_motors.configuration.jwt.JwtUtils;
import com.damtab.my_bachelor_motors.controller.ApiRegistration;
import com.damtab.my_bachelor_motors.controller.CarController;
import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.entity.OldCar;
import com.damtab.my_bachelor_motors.repository.CarRepository;
import com.damtab.my_bachelor_motors.service.CarService;
import com.damtab.my_bachelor_motors.service.UserCustomDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    @MockitoBean
    private UserCustomDetailsService userCustomDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private Car test1;
    private Car test2;
    private List<Car> myListCar;


    @BeforeEach
    public void initializeCar() {

        test1 = new Car();
        test1.setCarId(22L);
        test1.setBrand("BMW");
        test1.setModel("X2");
        test1.setMotorisation("250CV");
        test1.setKilometer(55000);
        test1.setPrice(25000);

        test2 = new Car();
        test2.setCarId(55L);
        test2.setBrand("FIAT");
        test2.setModel("500L");
        test2.setMotorisation("110CV");
        test2.setKilometer(120000);
        test2.setPrice(8000);

        myListCar = new ArrayList<>();
        myListCar.add(test1);
        myListCar.add(test2);

    }

    @Test
    public void getAllCar_shouldReturnList() throws Exception {

        Mockito.when(carService.getAllCars()).thenReturn(myListCar);
        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_CAR))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].carId").value(22))
                .andExpect(jsonPath("$[0].brand").value("BMW"))
                .andExpect(jsonPath("$[0].motorisation").value("250CV"))
                .andExpect(jsonPath("$[0].price").value(25000))
                .andExpect(jsonPath("$[1].carId").value(55))
                .andExpect(jsonPath("$[1].model").value("500L"))
                .andExpect(jsonPath("$[1].kilometer").value(120000))
                .andExpect(jsonPath("$[1].price").value(8000));
    }

    @Test
    public void getAllCar_shouldReturnFalse() throws Exception {

        Mockito.when(carService.getAllCars()).thenReturn(null);
        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_CAR))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCarById_shouldReturnCar_whenCarExist() throws Exception {
        Mockito.when(carService.getCarById(55L)).thenReturn(test2);
        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_CAR + "/55"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value(55))
                .andExpect(jsonPath("$.model").value("500L"))
                .andExpect(jsonPath("$.kilometer").value(120000))
                .andExpect(jsonPath("$.price").value(8000));
    }

    @Test
    public void getCarById_shouldReturnFalse_whenCarNotExist() throws Exception {
        Mockito.when(carService.getCarById(99L)).thenReturn(null);
        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_CAR + "/55"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void createdCar_shouldReturn201() throws Exception {
        mockMvc.perform(post(ApiRegistration.REST_API + ApiRegistration.REST_CAR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(test2)))
                .andExpect(status().isCreated());

        Mockito.verify(carService).createCar(Mockito.any(Car.class));
    }

    @Test
    public void deleteCarById_whenCarExist() throws Exception {
        Mockito.when(carService.deleteCar(55L)).thenReturn(true);
        try {
            mockMvc.perform(delete(ApiRegistration.REST_API + ApiRegistration.REST_CAR + "/55"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteCarById_shouldReturnFalse_whencarNotExist() throws Exception {
        Mockito.when(carService.deleteCar(99L)).thenReturn(false);
        mockMvc.perform(delete(ApiRegistration.REST_API + ApiRegistration.REST_CAR + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatedCarById_shouldUpdated_whenCarExist() throws Exception {
        Car newCar = new Car();
        newCar.setKilometer(110000);
        newCar.setPrice(15000);

        mockMvc.perform(put(ApiRegistration.REST_API + ApiRegistration.REST_CAR + "/22")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newCar)))
                .andExpect(status().isOk());

        Mockito.verify(carService).updateCar(Mockito.eq(22L), Mockito.any(Car.class));
    }
}
