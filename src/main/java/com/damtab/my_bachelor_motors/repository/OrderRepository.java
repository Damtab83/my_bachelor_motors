package com.damtab.my_bachelor_motors.repository;

import com.damtab.my_bachelor_motors.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
