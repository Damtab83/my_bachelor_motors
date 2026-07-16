package com.damtab.my_bachelor_motors.repository;

import com.damtab.my_bachelor_motors.entity.OldCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OldCarRepository extends JpaRepository<OldCar, Long> {
}
