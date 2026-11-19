package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.dto.mapper.CarMapper;
import com.damtab.my_bachelor_motors.dto.mapper.OrderCarMapper;
import com.damtab.my_bachelor_motors.dto.request.OrderCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.CarResponseDto;
import com.damtab.my_bachelor_motors.dto.response.OrderCarResponseDto;
import com.damtab.my_bachelor_motors.entity.OrderCar;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.OrderCarRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderCarService {

    private static final Logger log =
            LoggerFactory.getLogger(OrderCarService.class);

    //CRud for OrderCar for change Rent to Buy or Buy to Rent
    private final OrderCarRepository orderCarRepository;
    private final OrderCarMapper orderCarMapper;
    private final CarMapper carMapper;

    //Get List of OrderCar
    public List<OrderCarResponseDto> getAllOrders() {
        log.debug("Nombre de commandes récupérées ");
        return orderCarRepository.findAll()
                .stream()
                .map(orderCarMapper::toResponseDto)
                .toList();

    }

    //Get OrderCar By Id
    public OrderCarResponseDto getOrderById(Long id) {
        OrderCar orderCar = orderCarRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucune voiture trouvée avec l'identifiant : {}", id);
                    return new ResourceNotFoundException("Aucune commande trouvée");
                });

        return orderCarMapper.toResponseDto(orderCar);
    }

    //Create OrderCar
    @Transactional
    public OrderCarResponseDto createOrder(OrderCarRequestDto dto) {
//        OrderCar orderCar = orderCarMapper.toEntity(dto);
//
//        OrderCar savedOrder = orderCarRepository.save(orderCar);
//        log.info("Commande créée avec succès. Identifiant : {}", savedOrder.getId());
//        return orderCarMapper.toResponseDto(savedOrder);
        log.info("Car ID reçu : {}", dto.carId());
        log.info("User ID reçu : {}", dto.userId());

        OrderCar orderCar = orderCarMapper.toEntity(dto);

        // Vérification des associations avant sauvegarde
        log.info("Car avant save : {}",
                orderCar.getCar() != null
                        ? orderCar.getCar().getCarId() : null);

        log.info("User avant save : {}",
                orderCar.getUser() != null
                        ? orderCar.getUser().getId() : null);

        OrderCar savedOrder = orderCarRepository.save(orderCar);

        // Vérification après sauvegarde
        log.info("Car après save : {}",
                savedOrder.getCar() != null
                        ? savedOrder.getCar().getCarId() : null);

        log.info("User après save : {}",
                savedOrder.getUser() != null
                        ? savedOrder.getUser().getId() : null);

        // Vérification des DTO
        CarResponseDto carDto = carMapper.toResponseDto(savedOrder.getCar());

        log.info("Car DTO : {}", carDto);

        return orderCarMapper.toResponseDto(savedOrder);
    }

    //Delete OrderCar
    @Transactional
    public void deleteOrder(Long id) {
        OrderCar orderCar = orderCarRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Commande non trouvée"));
        log.info("Commande supprimée avec succès. Identifiant : {}", id);
        orderCarRepository.delete(orderCar);

    }

    //Update OrderCar for change Buy to Rent and inverse
    public OrderCarResponseDto updateOrder(Long id, OrderCarRequestDto dto) {
        OrderCar existingOrder = orderCarRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Commande non trouvée"));

        OrderCar updatedOrder = orderCarMapper.toEntity(dto);

        existingOrder.setCar(updatedOrder.getCar());
        existingOrder.setUser(updatedOrder.getUser());
        existingOrder.setOfferType(updatedOrder.getOfferType());
        existingOrder.setTradeOldCar(updatedOrder.getTradeOldCar());
        existingOrder.setSubscriptionInsurance(
                updatedOrder.isSubscriptionInsurance()
        );

        OrderCar savedOrder = orderCarRepository.save(existingOrder);

        return orderCarMapper.toResponseDto(savedOrder);
    }
}
