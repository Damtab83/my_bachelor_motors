package com.damtab.my_bachelor_motors.repository;

import com.damtab.my_bachelor_motors.entity.UserCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCustomRepository extends JpaRepository<UserCustom, Long> {
    UserCustom findByEmail(String email);

}
