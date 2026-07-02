package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.dto.LoginUserCustomDto;
import com.damtab.my_bachelor_motors.dto.RegisterUserCustomDto;
import com.damtab.my_bachelor_motors.entity.Role;
import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class RegistrationLoginService {

    private final UserCustomRepository userCustomRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> registerUserCustom(@RequestBody RegisterUserCustomDto dtoRegister) {
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


    public ResponseEntity<?> loginUserCustom(@RequestBody LoginUserCustomDto userCustom) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCustom.email(), userCustom.password()));
            return ResponseEntity.ok("Succès à la connection");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe invalide");
        }
    }
}
