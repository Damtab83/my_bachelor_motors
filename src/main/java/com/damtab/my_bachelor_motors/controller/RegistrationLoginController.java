package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.LoginUserCustomDto;
import com.damtab.my_bachelor_motors.dto.RegisterUserCustomDto;
import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.repository.UserCustomRepository;
import com.damtab.my_bachelor_motors.service.RegistrationLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RegistrationLoginController {

    private final RegistrationLoginService registrationLoginService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUserCustom(@RequestBody RegisterUserCustomDto dtoRegister) {

        return registrationLoginService.registerUserCustom(dtoRegister);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUserCustom(@RequestBody LoginUserCustomDto dtoLogin) {
        return registrationLoginService.loginUserCustom(dtoLogin);
    }
}
