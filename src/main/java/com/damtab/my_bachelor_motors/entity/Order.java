package com.damtab.my_bachelor_motors.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId", referencedColumnName = "carId")
    private Car car;

    @Enumerated(value = EnumType.STRING)
    private OfferType offerType;

    @OneToOne(optional = true)
    @JoinColumn(name = "oldCarId")
    private OldCar tradeOldCar;
    private boolean subscriptionInsurance;
}
