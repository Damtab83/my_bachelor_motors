package com.damtab.my_bachelor_motors.dto.mapper;

import com.damtab.my_bachelor_motors.dto.request.OrderCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.OrderCarResponseDto;
import com.damtab.my_bachelor_motors.entity.OrderCar;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.CarRepository;
import com.damtab.my_bachelor_motors.repository.OldCarRepository;
import com.damtab.my_bachelor_motors.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCarMapper {

    private final CarRepository carRepository;
    private final UserCustomRepository userCustomRepository;
    private final OldCarRepository oldCarRepository;
    private final CarMapper carMapper;
    private final OldCarMapper oldCarMapper;

    public OrderCar toEntity(OrderCarRequestDto dto) {
        OrderCar orderCar = new OrderCar();

        orderCar.setCar(
                carRepository.findById(dto.carId())
                        .orElseThrow(() -> new ResourceNotFoundException("Voiture non trouvée"))
        );
        orderCar.setUser(
                userCustomRepository.findById(dto.userId())
                        .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé"))
        );
        orderCar.setOfferType(dto.offerType());

        if(dto.oldCarId() != null) {
            orderCar.setTradeOldCar(
                    oldCarRepository.findById(dto.oldCarId())
                            .orElseThrow(()-> new ResourceNotFoundException("aucune ancienne voiture"))
            );
        }

        orderCar.setSubscriptionInsurance(dto.subscriptionInsurance());

        return orderCar;

    }

    public OrderCarResponseDto toResponseDto(OrderCar orderCar) {
        return new OrderCarResponseDto(
                orderCar.getId(),
                carMapper.toResponseDto(orderCar.getCar()),
                orderCar.getUser().getId(),
                orderCar.getOfferType(),
                orderCar.getTradeOldCar() != null
                        ? oldCarMapper.toResponseDto(orderCar.getTradeOldCar()) : null,
                orderCar.isSubscriptionInsurance()
        );
    }
}
