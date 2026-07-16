package com.damtab.my_bachelor_motors.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageCarId;
    private String name;
    private int size;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId")
    private Car car;
}
