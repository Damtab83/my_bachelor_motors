package com.damtab.my_bachelor_motors.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDriving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long testDrivingId;
    private LocalDateTime testDate;
    private boolean confirmed;
}
