package com.damtab.my_bachelor_motors.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class UserCustom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstname;

    private String lastname;

    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "userCustom", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderCar> oredersCars;
}
