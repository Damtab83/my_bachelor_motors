package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.OrderCar;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.OrderCarRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCarService {

    private static final Logger log =
            LoggerFactory.getLogger(OrderCarService.class);

    //CRud for OrderCar for change Rent to Buy or Buy to Rent
    private final OrderCarRepository orderRepository;

    //Get List of OrderCar
    public List<OrderCar> getAllOrders() {
        List<OrderCar> orders = orderRepository.findAll();
        log.debug("Nombre de commandes récupérées : {}", orders.size());
        if(orders.isEmpty()) {
            throw new ResourceNotFoundException("Aucunes commande trouvée");
        }
        return orders;
    }

    //Get OrderCar By Id
    public OrderCar getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucune voiture trouvée avec l'identifiant : {}", id);
                    return new ResourceNotFoundException("Aucune commande trouvée");
                });
    }

    //Create OrderCar
    public OrderCar createOrder(OrderCar newOrder) {

        OrderCar savedOrder = orderRepository.save(newOrder);
        log.info("Commande créée avec succès. Identifiant : {}", savedOrder.getId());
        return savedOrder;
    }

    //Delete OrderCar
    public boolean deleteOrder(Long id) {
        boolean toDelete = orderRepository.existsById(id);
        if (toDelete) {
            orderRepository.deleteById(id);
            log.info("Commande supprimée avec succès. Identifiant : {}", id);
        }
        return toDelete;
    }

    //Update OrderCar for change Buy to Rent and inverse
    public OrderCar updateOrder(Long id, OrderCar newOrder) {
        OrderCar oldOrder = this.getOrderById(id);
        if(oldOrder != null) {
            oldOrder.setCar(newOrder.getCar());
            oldOrder.setOfferType(newOrder.getOfferType());
            oldOrder.setTradeOldCar(newOrder.getTradeOldCar());
            oldOrder.setSubscriptionInsurance(newOrder.isSubscriptionInsurance());

            orderRepository.save(oldOrder);
            log.info("Commande modifiée avec succès. Identifiant : {}", id);
        }
        return oldOrder;
    }
}
