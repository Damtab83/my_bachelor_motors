package com.damtab.my_bachelor_motors.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
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
    @Future(message = "L'essai doit être organiserdans le futur")
    private LocalDateTime testDate;
    private boolean confirmed;
}
