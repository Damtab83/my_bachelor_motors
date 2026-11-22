package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.mapper.OrderCarMapper;
import com.damtab.my_bachelor_motors.dto.request.OrderCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.OrderCarResponseDto;
import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.entity.OrderCar;
import com.damtab.my_bachelor_motors.service.OrderCarService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_ORDER)
@RequiredArgsConstructor
public class OrderCarController {

    private static final Logger log =
            LoggerFactory.getLogger(OrderCarController.class);

    //Different routing for...
    private final OrderCarService orderCarService;


    //Get List of OrderCar
    @GetMapping
    public ResponseEntity<List<OrderCarResponseDto>> getAllOrder() {
        List<OrderCarResponseDto> myListOrder = orderCarService.getAllOrders();
        log.info("Requête GET terminée : {} commande(s) récupée(s)",
                myListOrder.size());
        return ResponseEntity.ok(myListOrder);
    }

    //Get OrderCar By Id
    @GetMapping("/{id}")
    public ResponseEntity<OrderCarResponseDto> getOrderById(@PathVariable Long id) {
        OrderCarResponseDto order = orderCarService.getOrderById(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("Requête GET reçue : récupération de commande ID={}", id);
        return ResponseEntity.ok(order);
    }

    //Create OrderCar
    @PostMapping
    public ResponseEntity<OrderCarResponseDto> createOrder(@RequestBody OrderCarRequestDto requestDto) {
        OrderCarResponseDto savedOrder = orderCarService.createOrder(requestDto);
       log.info("Requête POST terminée : commande ID={}",
               savedOrder.id());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedOrder);
    }

    //Delete OrderCar
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
        orderCarService.deleteOrder(id);
        log.info("Requête DELETE terminée : commande ID={} supprimée", id);
        return ResponseEntity.noContent().build();
    }

    //Update OrderCar
    @PutMapping("/{id}")
    public ResponseEntity<OrderCarResponseDto> updateOrder(@PathVariable Long id, @RequestBody OrderCarRequestDto requestDto) {
        OrderCarResponseDto orderToUpdate = orderCarService.updateOrder(id, requestDto);
        log.info("Requête PUT terminée : commande ID={} modifiée", id);
        return ResponseEntity.ok(orderToUpdate);
    }
}
