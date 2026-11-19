package com.damtab.my_bachelor_motors.serviceTest;

import com.damtab.my_bachelor_motors.dto.mapper.OrderCarMapper;
import com.damtab.my_bachelor_motors.dto.request.OrderCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.CarResponseDto;
import com.damtab.my_bachelor_motors.dto.response.OrderCarResponseDto;
import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.entity.OfferType;
import com.damtab.my_bachelor_motors.entity.OrderCar;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.OrderCarRepository;
import com.damtab.my_bachelor_motors.service.OrderCarService;
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
public class OrderCarServiceTest {

    @Mock
    private OrderCarRepository orderRepository;

    @Mock
    private OrderCarMapper orderCarMapper;

    @InjectMocks
    private OrderCarService orderService;

    private OrderCar order1;
    private OrderCar order2;
    private List<OrderCar> myListOrder;
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


        order1 = new OrderCar();
        order1.setId(44L);
        order1.setCar(carTest1);
        order1.setSubscriptionInsurance(false);
        order1.setOfferType(OfferType.BUY);

        order2 = new OrderCar();
        order2.setId(55L);
        order2.setCar(carTest2);
        order2.setSubscriptionInsurance(true);
        order2.setOfferType(OfferType.RENT);

        myListOrder = new ArrayList<>();
        myListOrder.add(order1);
        myListOrder.add(order2);
    }


    // Helper method to create a response DTO
    private OrderCarResponseDto createResponseDto(OrderCar order) {

        Car car = order.getCar();

        CarResponseDto carDto = new CarResponseDto(
                car.getCarId(),
                car.getBrand(),
                car.getModel(),
                car.getMotorisation(),
                car.getKilometer(),
                car.getPrice(),
                null,
                null
        );

        return new OrderCarResponseDto(
                order.getId(),
                carDto,
                null,
                order.getOfferType(),
                null,
                order.isSubscriptionInsurance()
        );
    }

    @Test
    public void getAllOrder_shouldReturnList() throws Exception {

        Mockito.when(orderRepository.findAll()).thenReturn(myListOrder);

        Mockito.when(orderCarMapper.toResponseDto(order1))
                .thenReturn(createResponseDto(order1));

        Mockito.when(orderCarMapper.toResponseDto(order2))
                .thenReturn(createResponseDto(order2));

        List<OrderCarResponseDto> result = orderService.getAllOrders();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(44L, result.get(0).id());
        assertEquals(OfferType.BUY, result.get(0).offerType());
        assertEquals("FIAT", result.get(0).car().brand());
        assertEquals(55L, result.get(1).id());
        assertEquals(OfferType.RENT, result.get(1).offerType());
        assertEquals("BMW", result.get(1).car().brand());
    }

    @Test
    public void getOrderById_shouldReturnOrder() throws Exception {
        Mockito.when(orderRepository.findById(55L))
                .thenReturn(Optional.of(order2));

        OrderCarResponseDto expectedDto = createResponseDto(order2);

        Mockito.when(orderCarMapper.toResponseDto(order2))
                .thenReturn(expectedDto);

        OrderCarResponseDto result = orderService.getOrderById(55L);

        assertNotNull(result);
        assertEquals(55L, result.id());
        assertEquals(OfferType.RENT, result.offerType());
        assertEquals("BMW", result.car().brand());

        Mockito.verify(orderRepository).findById(55L);
        Mockito.verify(orderCarMapper).toResponseDto(order2);
    }

    @Test
    public void getOrderById_shouldReturnFalse_whenNotExist() throws Exception {
        Mockito.when(orderRepository.findById(99L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> orderService.getOrderById(99L)
        );

        assertEquals("Aucune commande trouvée", exception.getMessage());
    }

    @Test
    public void createOrder_shouldReturn201_WhenOrderCreated() throws Exception {
        OrderCarRequestDto request = new OrderCarRequestDto(
                10L,
                1L,
                OfferType.BUY,
                null,
                false
        );

        Mockito.when(orderCarMapper.toEntity(request))
                .thenReturn(order1);

        Mockito.when(orderRepository.save(order1))
                .thenReturn(order1);

        Mockito.when(orderCarMapper.toResponseDto(order1))
                .thenReturn(createResponseDto(order1));

        OrderCarResponseDto result = orderService.createOrder(request);

        assertNotNull(result);
        assertEquals(44L, result.id());
        assertEquals(OfferType.BUY, result.offerType());

        Mockito.verify(orderRepository).save(order1);
    }

    @Test
    public void deleteOrderById_shouldDeleteOrder_whenOrderExist() throws Exception {
        Mockito.when(orderRepository.findById(55L))
                .thenReturn(Optional.of(order1));

        orderService.deleteOrder(55L);

        Mockito.verify(orderRepository).delete(order1);
    }

    @Test
    public void deleteOrderById_shouldNotDeleteOrder_whenOrderNotExist() throws Exception {
        Mockito.when(orderRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> orderService.deleteOrder(99L)
        );

        Mockito.verify(orderRepository, Mockito.never())
                .delete(Mockito.any(OrderCar.class));

    }

    @Test
    public void updatedOrderById_shouldModifyOrder_whenOrderExist() throws Exception {
        OrderCarRequestDto request = new OrderCarRequestDto(
                10L,
                1L,
                OfferType.BUY,
                null,
                false
        );

        Mockito.when(orderRepository.findById(55L))
                .thenReturn(Optional.of(order1));

        Mockito.when(orderCarMapper.toEntity(request))
                .thenReturn(order2);

        Mockito.when(orderRepository.save(Mockito.any(OrderCar.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(orderCarMapper.toResponseDto(order1))
                .thenAnswer(invocation -> createResponseDto(order1));

        OrderCarResponseDto result = orderService.updateOrder(55L, request);

        assertNotNull(result);
        assertEquals(44L, result.id());
        assertEquals(OfferType.RENT, result.offerType());

        Mockito.verify(orderRepository).save(order1);

    }

}
