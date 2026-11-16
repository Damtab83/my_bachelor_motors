package com.damtab.my_bachelor_motors.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImageCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageCarId;
    private String name;
    private long size;

    private String contentType;
    
    @Lob
    @JsonIgnore
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Car car;
}
