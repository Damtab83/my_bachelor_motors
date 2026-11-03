package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.configuration.jwt.JwtUtils;
import com.damtab.my_bachelor_motors.dto.LoginResponse;
import com.damtab.my_bachelor_motors.dto.LoginUserCustomDto;
import com.damtab.my_bachelor_motors.dto.LoginUserResponse;
import com.damtab.my_bachelor_motors.dto.RegisterUserCustomDto;
import com.damtab.my_bachelor_motors.entity.Role;
import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationLoginService {

    //Create and connecting account
    private final UserCustomRepository userCustomRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    //Create account for login
    public ResponseEntity<?> registerUserCustom(RegisterUserCustomDto dtoRegister) {
        if(userCustomRepository.findByEmail(dtoRegister.email()) != null) {
            return ResponseEntity.badRequest().body("L'utilisateur existe déjà");
        }

        UserCustom userCustom = new UserCustom();
        userCustom.setFirstname(dtoRegister.firstname());
        userCustom.setLastname(dtoRegister.lastname());
        userCustom.setEmail(dtoRegister.email());
        userCustom.setPassword(passwordEncoder.encode(dtoRegister.password()));
        userCustom.setRole(Role.ROLE_CUSTOM);


        userCustomRepository.save(userCustom);
        return ResponseEntity.ok("Utilisateur enregistré");
    }


    //Authentication with login or invalid false email
//    public ResponseEntity<?> loginUserCustom(LoginUserCustomDto userCustom) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCustom.email(), userCustom.password()));
//            String token = jwtUtils.generateToken(userCustom.email());
//            return ResponseEntity.ok(
//                    token);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe invalide");
//        }
//    }

    public ResponseEntity<?> loginUserCustom(LoginUserCustomDto credentials) {
        final String authenticatedEmail;

        try {
            authenticatedEmail = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.email(),
                            credentials.password()
                    )
            ).getName();
        } catch(AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou mot de passe invalide");
        }

        UserCustom userCustom = userCustomRepository.findByEmail(authenticatedEmail);
        if(userCustom == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Utilisateur introuvable");
        }

        String token = jwtUtils.generateToken(authenticatedEmail);

        LoginUserResponse userResponse = new LoginUserResponse(
                userCustom.getId(),
                userCustom.getFirstname(),
                userCustom.getLastname(),
                userCustom.getEmail(),
                toFrontendRole(userCustom.getRole())
        );
        return ResponseEntity.ok(new LoginResponse(token, userResponse));
    }

    private String toFrontendRole(Role role) {
        return switch(role.name()) {
            case "ROLE_ADMIN", "ADMIN" -> "Admin";
            case "ROLE_CUSTOM", "CUSTOM" -> "Custom";
            default -> throw new IllegalStateException(
                    "Role non pris en charge : " + role.name()
            );
        };
    }
}
