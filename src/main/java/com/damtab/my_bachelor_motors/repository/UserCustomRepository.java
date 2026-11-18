package com.damtab.my_bachelor_motors.repository;

import com.damtab.my_bachelor_motors.entity.UserCustom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCustomRepository extends JpaRepository<UserCustom, Long> {
    UserCustom findByEmail(String email);

}
