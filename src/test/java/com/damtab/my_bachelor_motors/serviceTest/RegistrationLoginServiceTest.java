package com.damtab.my_bachelor_motors.serviceTest;

import com.damtab.my_bachelor_motors.configuration.jwt.JwtUtils;
import com.damtab.my_bachelor_motors.dto.LoginResponse;
import com.damtab.my_bachelor_motors.dto.LoginUserCustomDto;
import com.damtab.my_bachelor_motors.dto.LoginUserResponse;
import com.damtab.my_bachelor_motors.dto.RegisterUserCustomDto;
import com.damtab.my_bachelor_motors.entity.Role;
import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.repository.UserCustomRepository;
import com.damtab.my_bachelor_motors.service.RegistrationLoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class RegistrationLoginServiceTest {

    @Mock
    private UserCustomRepository userCustomRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private RegistrationLoginService registrationLoginService;

    private UserCustom user1;

    @Test
    public void registration_shouldCreateUser() throws Exception {
        RegisterUserCustomDto registerTest = new RegisterUserCustomDto(
                "John",
                "Doe",
                "john.doe@test.fr",
                "john1234"
        );

        Mockito.when(userCustomRepository.findByEmail("john.doe@test.fr"))
                .thenReturn(null);

        Mockito.when(passwordEncoder.encode("john1234"))
                .thenReturn("passwordEncode");

        ResponseEntity<?> response =
                registrationLoginService.registerUserCustom(registerTest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Utilisateur enregistré", response.getBody());

        Mockito.verify(userCustomRepository)
                .findByEmail("john.doe@test.fr");

        Mockito.verify(passwordEncoder)
                .encode("john1234");

        Mockito.verify(userCustomRepository)
                .save(Mockito.any(UserCustom.class));

    }

    @Test
    public void registration_shouldFail_whenEmailAlreadyExists() {

        RegisterUserCustomDto registerTest = new RegisterUserCustomDto(
                "John",
                "Doe",
                "john.doe@test.fr",
                "john1234"
        );

        Mockito.when(userCustomRepository.findByEmail("john.doe@test.fr"))
                .thenReturn(new UserCustom());

        ResponseEntity<?> response =
                registrationLoginService.registerUserCustom(registerTest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("L'utilisateur existe déjà", response.getBody());

        Mockito.verify(userCustomRepository)
                .findByEmail("john.doe@test.fr");

        Mockito.verify(userCustomRepository, Mockito.never())
                .save(Mockito.any(UserCustom.class));

        Mockito.verify(passwordEncoder, Mockito.never())
                .encode(Mockito.anyString());
    }

    @Test
    public void loginUserCustom_shouldReturnToken_whenCredentialsAreCorrect() {

//        LoginUserCustomDto dto = new LoginUserCustomDto(
//                "john.doe@test.fr",
//                "Custom"
//        );
//
//        String token = "fake-jwt-token";
//
//        Mockito.when(jwtUtils.generateToken("john.doe@test.fr"))
//                .thenReturn(token);
//
//        ResponseEntity<?> response =
//                registrationLoginService.loginUserCustom(dto);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(token, response.getBody());
//
//        Mockito.verify(authenticationManager)
//                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
//
//        Mockito.verify(jwtUtils)
//                .generateToken("john.doe@test.fr");



        // 1. Identifiants de connexion
        LoginUserCustomDto dto = new LoginUserCustomDto(
                "john.doe@test.fr",
                "password"
        );

        // 2. Utilisateur simulé
        UserCustom userCustom = new UserCustom();
        userCustom.setId(1L);
        userCustom.setFirstname("John");
        userCustom.setLastname("Doe");
        userCustom.setEmail("john.doe@test.fr");
        userCustom.setRole(Role.ROLE_CUSTOM);

        // 3. Authentification simulée
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "john.doe@test.fr",
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_CUSTOM"))
                );

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(authentication);

        // 4. Repository simulé
        Mockito.when(userCustomRepository.findByEmail("john.doe@test.fr"))
                .thenReturn(userCustom);

        // 5. JWT simulé
        String token = "fake-jwt-token";

        Mockito.when(jwtUtils.generateToken("john.doe@test.fr"))
                .thenReturn(token);

        // 6. Exécution
        ResponseEntity<?> response =
                registrationLoginService.loginUserCustom(dto);

        // 7. Vérifications
        assertEquals(HttpStatus.OK, response.getStatusCode());

        LoginResponse body = (LoginResponse) response.getBody();

        assertNotNull(body);
        assertEquals(token, body.token());

        assertNotNull(body.userdto());
        assertEquals("John", body.userdto().firstname());
        assertEquals("Custom", body.userdto().role());

        Mockito.verify(authenticationManager)
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));

        Mockito.verify(userCustomRepository)
                .findByEmail("john.doe@test.fr");

        Mockito.verify(jwtUtils)
                .generateToken("john.doe@test.fr");
    }

    @Test
    public void loginUserCustom_shouldReturnUnauthorized_whenCredentialsAreIncorrect() {

        LoginUserCustomDto dto = new LoginUserCustomDto(

                "john.doe@test.fr",
                "Customier"
        );

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<?> response =
                registrationLoginService.loginUserCustom(dto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(
                "Email ou mot de passe invalide",
                response.getBody()
        );

        Mockito.verify(authenticationManager)
                .authenticate(Mockito.any());

        Mockito.verify(jwtUtils, Mockito.never())
                .generateToken(Mockito.anyString());
    }
}
