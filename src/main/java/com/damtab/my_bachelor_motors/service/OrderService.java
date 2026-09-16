package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.Order;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    //CRud for Order for change Rent to Buy or Buy to Rent
    private final OrderRepository orderRepository;

    //Get List of Order
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if(orders.isEmpty()) {
            throw new ResourceNotFoundException("Aucunes commande trouvée");
        }
        return orders;
    }

    //Get Order By Id
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aucune commande trouvée"));
    }

    //Create Order
    public void createOrder(Order newOrder) {
        orderRepository.save(newOrder);
    }

    //Delete Order
    public boolean deleteOrder(Long id) {
        Boolean toDelete = orderRepository.existsById(id);
        if (toDelete) {
            orderRepository.deleteById(id);
        }
        return toDelete;
    }

    //Update Order for change Buy to Rent and inverse
    public void updateOrder(Long id, Order newOrder) {
        Order oldOrder = this.getOrderById(id);
        if(oldOrder != null) {
            oldOrder.setCar(newOrder.getCar());
            oldOrder.setOfferType(newOrder.getOfferType());
            oldOrder.setTradeOldCar(newOrder.getTradeOldCar());
            oldOrder.setSubscriptionInsurance(newOrder.isSubscriptionInsurance());

            orderRepository.save(oldOrder);
        }
    }
}
