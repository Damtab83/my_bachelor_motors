package com.damtab.my_bachelor_motors.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carId;
    private String brand;
    private String model;
    private String motorisation;
    private int kilometer;
    private int price;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<ImageCar> imageCars;

    @OneToOne(optional = true)
    @JoinColumn(name = "testDrivingId")
    private TestDriving testDriving;
}
