package com.damtab.my_bachelor_motors.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "carId")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "user_custom_id", referencedColumnName = "id")
    private UserCustom user;

    @Enumerated(value = EnumType.STRING)
    private OfferType offerType;

    @OneToOne(optional = true)
    @JoinColumn(name = "old_car_id", referencedColumnName = "oldCarId")
    private OldCar oldCar;
    private boolean subscriptionInsurance;
}
