package com.damtab.my_bachelor_motors.controllerTest;

import com.damtab.my_bachelor_motors.configuration.jwt.JwtUtils;
import com.damtab.my_bachelor_motors.controller.ApiRegistration;
import com.damtab.my_bachelor_motors.controller.RegistrationLoginController;
import com.damtab.my_bachelor_motors.dto.LoginUserCustomDto;
import com.damtab.my_bachelor_motors.dto.RegisterUserCustomDto;
import com.damtab.my_bachelor_motors.service.RegistrationLoginService;
import com.damtab.my_bachelor_motors.service.UserCustomDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RegistrationLoginController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegistrationLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegistrationLoginService registrationLoginService;

    @MockitoBean
    private UserCustomDetailsService userCustomDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @Test
    public void registerUserCustom_shouldReturnOk_whenRegistrationIsOk() throws Exception {
        RegisterUserCustomDto registerTest = new RegisterUserCustomDto(
                "Jane",
                "Tarzan",
                "jane.tarzan@test.fr",
                "jane1234"
        );

        Mockito.doReturn(ResponseEntity.ok("Utilisateur enregistré"))
                .when(registrationLoginService)
                .registerUserCustom(Mockito.any(RegisterUserCustomDto.class));

        mockMvc.perform( post(ApiRegistration.REST_API + ApiRegistration.REST_AUTHENTICATION + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerTest)))
                .andExpect(status().isOk()) .andExpect(content().string("Utilisateur enregistré"));

        Mockito.verify(registrationLoginService)
                .registerUserCustom(Mockito.any(RegisterUserCustomDto.class));
    }

    @Test public void registerUserCustom_shouldReturnBadRequest_whenUserAlreadyExists() throws Exception {
        RegisterUserCustomDto registerDto = new RegisterUserCustomDto(
                "John",
                "Doe",
                "john.doe@test.fr",
                "john1234"
        );

        Mockito.doReturn(ResponseEntity.badRequest()
                        .body("L'utilisateur existe déjà"))
                .when(registrationLoginService)
                .registerUserCustom(Mockito.any(RegisterUserCustomDto.class));

        mockMvc.perform( post(ApiRegistration.REST_API + ApiRegistration.REST_AUTHENTICATION + "/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("L'utilisateur existe déjà"));
    }

    @Test public void loginUserCustom_shouldReturnOk_whenCredentialsAreCorrect() throws Exception {
        LoginUserCustomDto loginDto = new LoginUserCustomDto( "john.doe@test.fr", "john1234" );

        Mockito.doReturn(ResponseEntity.ok("mon-token-jwt"))
                .when(registrationLoginService)
                .loginUserCustom(Mockito.any(LoginUserCustomDto.class));

        mockMvc.perform( post(ApiRegistration.REST_API + ApiRegistration.REST_AUTHENTICATION + "/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginDto)) )
                .andExpect(status().isOk()) .andExpect(content().string("mon-token-jwt"));

        Mockito.verify(registrationLoginService) .loginUserCustom(Mockito.any(LoginUserCustomDto.class));
    }

    @Test public void loginUserCustom_shouldReturnUnauthorized_whenCredentialsAreIncorrect() throws Exception {
        LoginUserCustomDto loginDto = new LoginUserCustomDto( "john.doe@test.fr", "mauvaisMotDePasse" );

        Mockito.doReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe invalide"))
                .when(registrationLoginService)
                .loginUserCustom(Mockito.any(LoginUserCustomDto.class));

        mockMvc.perform( post(ApiRegistration.REST_API + ApiRegistration.REST_AUTHENTICATION + "/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content()
                        .string("Email ou mot de passe invalide")); }
}
