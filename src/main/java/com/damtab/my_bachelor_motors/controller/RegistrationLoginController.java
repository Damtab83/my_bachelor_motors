package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.LoginUserCustomDto;
import com.damtab.my_bachelor_motors.dto.RegisterUserCustomDto;
import com.damtab.my_bachelor_motors.service.RegistrationLoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_AUTHENTICATION)
@RequiredArgsConstructor
public class RegistrationLoginController {

    private static final Logger log =
            LoggerFactory.getLogger(RegistrationLoginController.class);

    private final RegistrationLoginService registrationLoginService;

    //Create new User
    @PostMapping(ApiRegistration.REST_REGISTRATION)
    public ResponseEntity<?> registerUserCustom(@RequestBody RegisterUserCustomDto dtoRegister) {
        log.info("Requête POST terminée : client créée");
        return registrationLoginService.registerUserCustom(dtoRegister);
    }

    //Connect User
    @PostMapping(ApiRegistration.REST_LOGIN)
    public ResponseEntity<?> loginUserCustom(@RequestBody LoginUserCustomDto dtoLogin) {

        log.info("Requête POST terminée : client connecté");
        return registrationLoginService.loginUserCustom(dtoLogin);
    }
}
