package com.damtab.my_bachelor_motors.repository;

import com.damtab.my_bachelor_motors.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
