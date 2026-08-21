package com.damtab.my_bachelor_motors.controllerTest;

import com.damtab.my_bachelor_motors.configuration.jwt.JwtUtils;
import com.damtab.my_bachelor_motors.controller.ApiRegistration;
import com.damtab.my_bachelor_motors.controller.OrderController;
import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.entity.OfferType;
import com.damtab.my_bachelor_motors.entity.Order;
import com.damtab.my_bachelor_motors.service.OrderService;
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

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private UserCustomDetailsService userCustomDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    private Order order1;
    private Order order2;
    private List<Order> myOrderList;
    private Car carTest1;
    private Car carTest2;

    @BeforeEach
    public void initialize() {

        carTest1 =new Car();
        carTest1.setCarId(10L);
        carTest1.setBrand("FIAT");
        carTest1.setModel("500X");
        carTest1.setKilometer(15000);
        carTest1.setMotorisation("Electrique");
        carTest1.setPrice(8000);

        carTest2 =new Car();
        carTest2.setCarId(20L);
        carTest2.setBrand("BMW");
        carTest2.setModel("X3");
        carTest2.setKilometer(25000);
        carTest2.setMotorisation("150CV");
        carTest2.setPrice(5000);

        order1 = new Order();
        order1.setId(33L);
        order1.setCar(carTest1);
        order1.setOfferType(OfferType.BUY);

        order2 = new Order();
        order2.setId(55L);
        order2.setCar(carTest2);
        order2.setOfferType(OfferType.RENT);

        myOrderList = new ArrayList<>();
        myOrderList.add(order1);
        myOrderList.add(order2);
    }

    @Test
    public void getAllOrder_shouldReturnOrderList() throws Exception {
        Mockito.when(orderService.getAllOrders()).thenReturn(myOrderList);
        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_ORDER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(33))
                .andExpect(jsonPath("$[0].offerType").value("BUY"))
                .andExpect(jsonPath("$[0].car.brand").value("FIAT"))
                .andExpect(jsonPath("$[0].car.model").value("500X"));
    }

    @Test
    public void getOrderById_shouldReturnOrder() throws Exception {
        Mockito.when(orderService.getOrderById(33L)).thenReturn(order1);
        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_ORDER + "/33"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(33))
                .andExpect(jsonPath("$.offerType").value("BUY"))
                .andExpect(jsonPath("$.car.brand").value("FIAT"))
                .andExpect(jsonPath("$.car.model").value("500X"));
    }

    @Test
    public void createOrder_shouldReturn201() throws Exception {

        mockMvc.perform(post(ApiRegistration.REST_API + ApiRegistration.REST_ORDER)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order2)))
                .andExpect(status().isCreated());

        Mockito.verify(orderService).createOrder(Mockito.any(Order.class));
    }

    @Test
    public void deleteOrderById_shouldReturn201_whenOrderExist() throws Exception {
        Mockito.when(orderService.deleteOrder(55L)).thenReturn(true);
        mockMvc.perform(delete(ApiRegistration.REST_API + ApiRegistration.REST_ORDER + "/55"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrderById_shouldModifyOrder_whenOrderExist() throws Exception {
        Order newOrder = new Order();
        newOrder.setOfferType(OfferType.BUY);
        newOrder.setCar(carTest1);
        newOrder.setCar(carTest1);

        mockMvc.perform(put(ApiRegistration.REST_API + ApiRegistration.REST_ORDER+ "/55")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isOk());

        Mockito.verify(orderService).updateOrder(Mockito.eq(55L), Mockito.any(Order.class));
    }
}
