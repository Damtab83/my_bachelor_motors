    package com.damtab.my_bachelor_motors.entity;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Pattern;
    import jakarta.validation.constraints.Size;
    import lombok.Data;

    import java.util.ArrayList;
    import java.util.List;

    @Data
    @Entity
    public class UserCustom {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Size(min = 1, max = 30)
        @Pattern(
                regexp = "/[a-zA-Z -]+$",
                message = "Le prénom contient des caractères invalides"
        )
        private String firstname;

        @NotBlank
        @Size(min = 1, max = 30)
        @Pattern(
                regexp = "/[a-zA-Z -]+$",
                message = "Le nom contient des caractères invalides"
        )
        private String lastname;

        @NotBlank
        @Size(min = 1, max = 30)
        @Email
        private String email;

        @NotBlank
        @Size(min = 8, max = 60)
        @Pattern(
                regexp = "/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/",
                message = "L'email contient des caractères invalides"
        )
        @JsonIgnore
        private String password;

        @Enumerated(value = EnumType.STRING)
        private Role role;

        @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
        private List<OrderCar> ordersCars = new ArrayList<>();
    }
