package com.damtab.my_bachelor_motors.controllerTest;

import com.damtab.my_bachelor_motors.configuration.jwt.JwtUtils;
import com.damtab.my_bachelor_motors.controller.ImageCarController;
import com.damtab.my_bachelor_motors.entity.ImageCar;
import com.damtab.my_bachelor_motors.service.ImageCarService;
import com.damtab.my_bachelor_motors.service.UserCustomDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageCarController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ImageCarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ImageCarService imageCarService;

    @MockitoBean
    private UserCustomDetailsService userCustomDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllImageCar_shouldReturnList() throws Exception {
        ImageCar imageCar = new ImageCar();
        imageCar.setImageCarId(33L);
        imageCar.setName("image_BMW");
        imageCar.setSize(333);

        Mockito.when(imageCarService.getAllImageCars()).thenReturn(List.of(imageCar));

        mockMvc.perform(get("/api/image-voiture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].size").value(333));
    }

    @Test
    public void getImageCarById_shouldReturn200_whenExists() throws Exception {
        ImageCar imageCar = new ImageCar();
        imageCar.setImageCarId(33L);
        imageCar.setName("image_BMW");
        imageCar.setSize(333);

        Mockito.when(imageCarService.getImageCarById(33L)).thenReturn(imageCar);

        mockMvc.perform(get("/api/image-voiture/33"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imageCarId").value(33))
                .andExpect(jsonPath("$.size").value(333));
    }

    @Test
    public void createImageCar_shouldReturn201() throws Exception {
        ImageCar test3 = new ImageCar();
        test3.setName("image_FIAT");
        test3.setSize(4444);

        mockMvc.perform(post("/api/image-voiture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(test3)))
                .andExpect(status().isCreated());

        Mockito.verify(imageCarService).createImageCar(Mockito.any(ImageCar.class));
    }

    @Test
    public void deleteImageCar_shouldReturn200_whenDelete() throws Exception {
        Mockito.when(imageCarService.deleteImageCar(33L)).thenReturn(true);
        mockMvc.perform(delete("/api/image-voiture/33"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteImageCars_shouldReturn404_whenNotFound() throws Exception {
        Mockito.when(imageCarService.deleteImageCar(99L)).thenReturn(false);
        mockMvc.perform(delete("/api/image-voiture/99"))
                .andExpect(status().isNotFound());
    }
}
