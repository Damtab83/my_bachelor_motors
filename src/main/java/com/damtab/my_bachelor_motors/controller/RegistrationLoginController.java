package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.LoginUserCustomDto;
import com.damtab.my_bachelor_motors.dto.RegisterUserCustomDto;
import com.damtab.my_bachelor_motors.service.RegistrationLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_AUTHENTICATION)
@RequiredArgsConstructor
public class RegistrationLoginController {

    private final RegistrationLoginService registrationLoginService;

    @PostMapping(ApiRegistration.REST_REGISTRATION)
    public ResponseEntity<?> registerUserCustom(@RequestBody RegisterUserCustomDto dtoRegister) {

        return registrationLoginService.registerUserCustom(dtoRegister);
    }

    @PostMapping(ApiRegistration.REST_LOGIN)
    public ResponseEntity<?> loginUserCustom(@RequestBody LoginUserCustomDto dtoLogin) {
        return registrationLoginService.loginUserCustom(dtoLogin);
    }
}
