package com.damtab.my_bachelor_motors.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OldCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long oldCarId;


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
    @Size(min = 3, max = 6)
    @Pattern(
            regexp = "/[0-9 -]+$",
            message = "Le prix contient des caractères invalides"
    )
    private int price;
}
