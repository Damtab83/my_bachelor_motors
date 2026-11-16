package com.damtab.my_bachelor_motors.dto.mapper;

import com.damtab.my_bachelor_motors.dto.request.OrderCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.OrderCarResponseDto;
import com.damtab.my_bachelor_motors.entity.OrderCar;
import com.damtab.my_bachelor_motors.repository.CarRepository;
import com.damtab.my_bachelor_motors.repository.OldCarRepository;
import com.damtab.my_bachelor_motors.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCarMapper {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserCustomRepository userCustomRepository;
    @Autowired
    private OldCarRepository oldCarRepository;

    public OrderCar toEntity(OrderCarRequestDto dto) {
        OrderCar orderCar = new OrderCar();

        orderCar.setCar(
                carRepository.findById(dto.carId())
                        .orElseThrow(() -> new RuntimeException("Voiture non trouvée"))
        );
        orderCar.setUser(
                userCustomRepository.findById(dto.userId())
                        .orElseThrow(() -> new RuntimeException("Client non trouvé"))
        );
        orderCar.setOfferType(dto.offerType());

        if(dto.oldCarId() != null) {
            orderCar.setTradeOldCar(
                    oldCarRepository.findById(dto.oldCarId())
                            .orElseThrow(()-> new RuntimeException("aucune ancienne voiture"))
            );
        }

        orderCar.setSubscriptionInsurance(dto.subscriptionInsurance());

        return orderCar;

    }

    public OrderCarResponseDto toResponseDto(OrderCar orderCar) {
        return new OrderCarResponseDto(
                orderCar.getId(),
                orderCar.getCar() != null ? orderCar.getCar().getCarId() : null,
                orderCar.getUser() != null ? orderCar.getUser().getId() : null,
                orderCar.getOfferType(),
                orderCar.getTradeOldCar() != null ? orderCar.getTradeOldCar().getOldCarId() : null,
                orderCar.isSubscriptionInsurance()
        );
    }
}
