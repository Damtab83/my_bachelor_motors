package com.damtab.my_bachelor_motors.controllerTest;

import com.damtab.my_bachelor_motors.configuration.jwt.JwtUtils;
import com.damtab.my_bachelor_motors.controller.ApiRegistration;
import com.damtab.my_bachelor_motors.controller.OrderCarController;
import com.damtab.my_bachelor_motors.dto.mapper.OrderCarMapper;
import com.damtab.my_bachelor_motors.entity.OfferType;
import com.damtab.my_bachelor_motors.service.OrderCarService;
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
import com.damtab.my_bachelor_motors.dto.request.OrderCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.OrderCarResponseDto;
import com.damtab.my_bachelor_motors.dto.response.CarResponseDto;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderCarController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderCarMapper orderCarMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderCarService orderService;

    @MockitoBean
    private UserCustomDetailsService userCustomDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    private OrderCarResponseDto response1;
    private OrderCarResponseDto response2;

    @BeforeEach
    public void initialize() {

        CarResponseDto carResponse1 = new CarResponseDto(
                10L, "FIAT", "500X", "Electrique",
                15000, 8000, List.of(), null
        );

        CarResponseDto carResponse2 = new CarResponseDto(
                20L, "BMW", "X3", "150CV",
                25000, 5000, List.of(), null
        );

        response1 = new OrderCarResponseDto(
                33L, carResponse1, 1L, OfferType.BUY, null, false
        );

        response2 = new OrderCarResponseDto(
                55L, carResponse2, 2L, OfferType.RENT, null, true
        );
    }

    @Test
    public void getAllOrder_shouldReturnOrderList() throws Exception {

        Mockito.when(orderService.getAllOrders())
                .thenReturn(List.of(response1, response2));

        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_ORDER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(33))
                .andExpect(jsonPath("$[0].offerType").value("BUY"))
                .andExpect(jsonPath("$[0].car.brand").value("FIAT"))
                .andExpect(jsonPath("$[0].car.model").value("500X"));
    }

    @Test
    public void getAllOrder_shouldReturnFalse() throws Exception {

        Mockito.when(orderService.getAllOrders())
                .thenReturn(List.of());

        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_ORDER))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }


    @Test
    public void getOrderById_shouldReturnOrder() throws Exception {

        Mockito.when(orderService.getOrderById(33L))
                .thenReturn(response1);

        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_ORDER + "/33"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(33))
                .andExpect(jsonPath("$.offerType").value("BUY"))
                .andExpect(jsonPath("$.car.brand").value("FIAT"));
    }

    @Test
    public void getOrderById_shouldReturnFalse() throws Exception {
        Mockito.when(orderService.getOrderById(99L)).thenReturn(null);
        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_ORDER + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createOrder_shouldReturn201() throws Exception {

        OrderCarRequestDto request = new OrderCarRequestDto(
                20L, 2L, OfferType.RENT, null, true
        );

        Mockito.when(orderService.createOrder(Mockito.any(OrderCarRequestDto.class)))
                .thenReturn(response2);

        mockMvc.perform(post(ApiRegistration.REST_API + ApiRegistration.REST_ORDER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Mockito.verify(orderService)
                .createOrder(Mockito.any(OrderCarRequestDto.class));
    }

    @Test
    public void deleteOrderById_shouldReturn201_whenOrderExist() throws Exception {

        Mockito.doNothing().when(orderService).deleteOrder(55L);

        mockMvc.perform(delete(
                        ApiRegistration.REST_API + ApiRegistration.REST_ORDER + "/55"))
                .andExpect(status().isNoContent());

        Mockito.verify(orderService).deleteOrder(55L);
    }

    @Test
    public void deleteOrderById_shouldReturnFalse_whenNotExist() throws Exception {

        Mockito.doThrow(new RuntimeException("Commande non trouvée"))
                .when(orderService).deleteOrder(88L);

        mockMvc.perform(delete(
                        ApiRegistration.REST_API + ApiRegistration.REST_ORDER + "/88"))
                .andExpect(status().isNotFound());

        Mockito.verify(orderService).deleteOrder(88L);
    }

    @Test
    public void updateOrderById_shouldModifyOrder_whenOrderExist() throws Exception {

        OrderCarRequestDto request = new OrderCarRequestDto(
                10L, 1L, OfferType.BUY, null, false
        );

        Mockito.when(orderService.updateOrder(
                Mockito.eq(55L),
                Mockito.any(OrderCarRequestDto.class)
        )).thenReturn(response1);

        mockMvc.perform(put(ApiRegistration.REST_API + ApiRegistration.REST_ORDER + "/55")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(orderService).updateOrder(
                Mockito.eq(55L),
                Mockito.any(OrderCarRequestDto.class)
        );
    }
}
