package com.damtab.my_bachelor_motors.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carId;
    @Size(min = 3, max = 20)
    @Pattern(
            regexp = "/[a-zA-Z -]+$",
            message = "La marque contient des caractères invalides"
    )
    private String brand;
    @Size(min = 3, max = 20)
    @Pattern(
            regexp = "/[a-zA-Z0-9 -]+$",
            message = "Le modèle contient des caractères invalides"
    )
    private String model;
    @Size(min = 1, max = 7)
    @Pattern(
            regexp = "/[a-zA-Z0-9 -]+$"
    )
    private String motorisation;
    @Size(min = 1, max = 7)
    @Pattern(
            regexp = "/[0-9 -]+$",
            message = "Le prix contient des caractères invalides"
    )
    private int kilometer;
    @Size(min = 3, max = 6)
    @Pattern(
            regexp = "/[0-9 -]+$",
            message = "Le prix contient des caractères invalides"
    )
    private int price;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<ImageCar> imageCars;

    @OneToOne(optional = true)
    @JoinColumn(name = "testDrivingId")
    private TestDriving testDriving;

    @OneToMany(mappedBy = "car", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderCar> ordersCars;
}
