package com.damtab.my_bachelor_motors.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OldCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long oldCarId;
    private String brand;
    private String model;
    private int price;
}
