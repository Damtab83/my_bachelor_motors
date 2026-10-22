package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.OrderCar;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.OrderCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCarService {

    //CRud for OrderCar for change Rent to Buy or Buy to Rent
    private final OrderCarRepository orderRepository;

    //Get List of OrderCar
    public List<OrderCar> getAllOrders() {
        List<OrderCar> orders = orderRepository.findAll();
        if(orders.isEmpty()) {
            throw new ResourceNotFoundException("Aucunes commande trouvée");
        }
        return orders;
    }

    //Get OrderCar By Id
    public OrderCar getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aucune commande trouvée"));
    }

    //Create OrderCar
    public void createOrder(OrderCar newOrder) {
        orderRepository.save(newOrder);
    }

    //Delete OrderCar
    public boolean deleteOrder(Long id) {
        Boolean toDelete = orderRepository.existsById(id);
        if (toDelete) {
            orderRepository.deleteById(id);
        }
        return toDelete;
    }

    //Update OrderCar for change Buy to Rent and inverse
    public void updateOrder(Long id, OrderCar newOrder) {
        OrderCar oldOrder = this.getOrderById(id);
        if(oldOrder != null) {
            oldOrder.setCar(newOrder.getCar());
            oldOrder.setOfferType(newOrder.getOfferType());
            oldOrder.setTradeOldCar(newOrder.getTradeOldCar());
            oldOrder.setSubscriptionInsurance(newOrder.isSubscriptionInsurance());

            orderRepository.save(oldOrder);
        }
    }
}
