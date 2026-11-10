package com.damtab.my_bachelor_motors.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestDriving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long testDrivingId;
    private LocalDateTime testDate;
    private boolean confirmed;
}
