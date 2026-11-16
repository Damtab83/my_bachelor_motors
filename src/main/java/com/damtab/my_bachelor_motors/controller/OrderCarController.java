package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.mapper.OrderCarMapper;
import com.damtab.my_bachelor_motors.dto.request.OrderCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.OrderCarResponseDto;
import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.entity.OrderCar;
import com.damtab.my_bachelor_motors.service.OrderCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_ORDER)
public class OrderCarController {

    //Different routing for...
    @Autowired
    private OrderCarService orderService;
    @Autowired
    private OrderCarMapper orderCarMapper;

    public OrderCarController(OrderCarService orderService, OrderCarMapper orderCarMapper) {
        this.orderService = orderService;
        this.orderCarMapper = orderCarMapper;
    }

    //Get List of OrderCar
    @GetMapping
    public ResponseEntity<List<OrderCarResponseDto>> getAllOrder() {
        List<OrderCarResponseDto> myListOrder = orderService.getAllOrders()
                .stream()
                .map(orderCarMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(myListOrder);
    }

    //Get OrderCar By Id
    @GetMapping("/{id}")
    public ResponseEntity<OrderCarResponseDto> getOrderById(@PathVariable Long id) {
        OrderCar order = orderService.getOrderById(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderCarMapper.toResponseDto(order));
    }

    //Create OrderCar
    @PostMapping
    public ResponseEntity<OrderCarResponseDto> createOrder(@RequestBody OrderCarRequestDto requestDto) {
        OrderCar newOrder = orderCarMapper.toEntity(requestDto);
        OrderCar savedOldCar = orderService.createOrder(newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderCarMapper.toResponseDto(savedOldCar));
    }

    //Delete OrderCar
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
        boolean toDelete = orderService.deleteOrder(id);
        return toDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //Update OrderCar
    @PutMapping("/{id}")
    public ResponseEntity<OrderCarResponseDto> updateOrder(@PathVariable Long id, @RequestBody OrderCarRequestDto requestDto) {
        OrderCar orderToUpdate = orderCarMapper.toEntity(requestDto);
        OrderCar newOrder = orderService.updateOrder(id, orderToUpdate);
        return ResponseEntity.ok(orderCarMapper.toResponseDto(newOrder));
    }
}
