package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.entity.Order;
import com.damtab.my_bachelor_motors.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_ORDER)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Object> getAllOrder() {
        List<Order> myListOrder = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(myListOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody Order newOrder) {
        orderService.createOrder(newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
        Boolean toDelete = orderService.deleteOrder(id);
        return toDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody Order newOrder) {
        orderService.updateOrder(id, newOrder);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
