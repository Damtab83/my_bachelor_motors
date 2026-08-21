package com.damtab.my_bachelor_motors.serviceTest;

import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.entity.OfferType;
import com.damtab.my_bachelor_motors.entity.Order;
import com.damtab.my_bachelor_motors.repository.OrderRepository;
import com.damtab.my_bachelor_motors.service.OrderService;
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
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order1;
    private Order order2;
    private List<Order> myListOrder;
    private Car carTest1;
    private Car carTest2;

    @BeforeEach
    public void initializeOrder() {

        carTest1 = new Car();
        carTest1.setCarId(10L);
        carTest1.setBrand("FIAT");
        carTest1.setModel("500");
        carTest1.setPrice(5000);
        carTest1.setKilometer(90000);
        carTest1.setMotorisation("Electrique");

        carTest2 = new Car();
        carTest2.setCarId(20L);
        carTest2.setBrand("BMW");
        carTest2.setModel("X1");
        carTest2.setPrice(15000);
        carTest2.setKilometer(150000);
        carTest2.setMotorisation("250CV");


        order1 = new Order();
        order1.setId(44L);
        order1.setCar(carTest1);
        order1.setSubscriptionInsurance(false);
        order1.setOfferType(OfferType.BUY);

        order2 = new Order();
        order2.setId(55L);
        order2.setCar(carTest2);
        order2.setSubscriptionInsurance(true);
        order2.setOfferType(OfferType.RENT);

        myListOrder = new ArrayList<>();
        myListOrder.add(order1);
        myListOrder.add(order2);
    }

    @Test
    public void getAllOrder_shouldReturnList() throws Exception {

        Mockito.when(orderRepository.findAll()).thenReturn(myListOrder);

        List<Order> result = orderService.getAllOrders();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(44L, result.get(0).getId());
        assertEquals(OfferType.BUY, result.get(0).getOfferType());
        assertEquals("FIAT", result.get(0).getCar().getBrand());
        assertEquals(55L, result.get(1).getId());
        assertEquals(OfferType.RENT, result.get(1).getOfferType());
        assertEquals("BMW", result.get(1).getCar().getBrand());
    }

    @Test
    public void getOrderById_shouldReturnOrder() throws Exception {
        Mockito.when(orderRepository.findById(55L)).thenReturn(Optional.of(order2));
        Order result = orderService.getOrderById(55L);
        assertNotNull(result);
        assertEquals(55L, result.getId());
        assertEquals(OfferType.RENT, result.getOfferType());
        assertEquals("BMW", result.getCar().getBrand());
    }

    @Test
    public void createOrder_shouldReturn201_WhenOrderCreated() throws Exception {
        orderService.createOrder(order1);
        Mockito.verify(orderRepository).save(order1);
    }

    @Test
    public void deleteOrderById_shouldDeleteOrder_whenOrderExist() throws Exception {
        Mockito.when(orderRepository.existsById(55L)).thenReturn(true);
        Boolean result = orderService.deleteOrder(55L);
        assertTrue(result);
        Mockito.verify(orderRepository).deleteById(55L);
    }

    @Test
    public void updatedOrderById_shouldModifyOrder_whenOrderExist() throws Exception {
        Order newOrder = new Order();
        newOrder.setOfferType(OfferType.RENT);
        newOrder.setCar(carTest1);

        Mockito.when(orderRepository.findById(44L)).thenReturn(Optional.of(order1));

        orderService.updateOrder(44L, newOrder);

        assertEquals(44L, order1.getId());
        assertEquals(OfferType.RENT, order1.getOfferType());
        assertNotNull(order1.getCar());
        assertEquals(10L, order1.getCar().getCarId());
        assertEquals("FIAT", order1.getCar().getBrand());

        Mockito.verify(orderRepository).findById(44L);
        Mockito.verify(orderRepository).save(order1);
    }

}
