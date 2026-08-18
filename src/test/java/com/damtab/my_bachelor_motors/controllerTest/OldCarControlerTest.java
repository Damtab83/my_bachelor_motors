package com.damtab.my_bachelor_motors.controllerTest;

import com.damtab.my_bachelor_motors.configuration.jwt.JwtUtils;
import com.damtab.my_bachelor_motors.controller.OldCarController;
import com.damtab.my_bachelor_motors.entity.OldCar;
import com.damtab.my_bachelor_motors.service.OldCarService;
import com.damtab.my_bachelor_motors.service.UserCustomDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OldCarController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OldCarControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OldCarService oldCarService;

    @MockitoBean
    private UserCustomDetailsService userCustomDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllOldCar_shouldReturnList() throws Exception {

        OldCar oldCarTest = new OldCar();
        oldCarTest.setOldCarId(11L);
        oldCarTest.setBrand("OPEL");
        oldCarTest.setModel("Zafira");
        oldCarTest.setPrice(10000);

        Mockito.when(oldCarService.getAllOldCars()).thenReturn(List.of(oldCarTest));

        mockMvc.perform(get("/api/ancienne-voiture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].oldCarId").value(11))
                .andExpect(jsonPath("$[0].brand").value("OPEL"))
                .andExpect(jsonPath("$[0].model").value("Zafira"))
                .andExpect(jsonPath("$[0].price").value(10000));
    }

    @Test
    public void getOldCarById_shouldReturnOldCar_whenExist() throws Exception {

        OldCar oldCarTest = new OldCar();
        oldCarTest.setOldCarId(22L);
        oldCarTest.setBrand("BMW");
        oldCarTest.setModel("X2");
        oldCarTest.setPrice(15000);

        Mockito.when(oldCarService.getOldCarById(22L)).thenReturn(Optional.of(oldCarTest));

        mockMvc.perform(get("/api/ancienne-voiture/22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.oldCarId").value(22))
                .andExpect(jsonPath("$.brand").value("BMW"))
                .andExpect(jsonPath("$.model").value("X2"))
                .andExpect(jsonPath("$.price").value(15000
                ));
    }

    @Test
    public void createOldCar_shouldReturn201() throws Exception {
        OldCar test3 = new OldCar();
        test3.setBrand("FIAT");
        test3.setModel("L");
        test3.setPrice(44444);

        mockMvc.perform(post("/api/ancienne-voiture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(test3)))
                .andExpect(status().isCreated());

        Mockito.verify(oldCarService).createOldCar(Mockito.any(OldCar.class));
    }

    @Test
    public void deleteOldCar_shouldReturn200_whenDelete() throws Exception {
        Mockito.when(oldCarService.deleteOldCar(22L)).thenReturn(true);
        mockMvc.perform(delete("/api/ancienne-voiture/22"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOldCars_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(oldCarService.deleteOldCar(99L)).thenReturn(false);
        mockMvc.perform(delete("/api/ancienne-voiture/99"))
                .andExpect(status().isNotFound());
    }
}
