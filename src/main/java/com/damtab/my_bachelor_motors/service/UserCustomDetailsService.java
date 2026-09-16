package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserCustomDetailsService implements UserDetailsService {

    //Loading User when exist in Database
    private final UserCustomRepository userCustomRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserCustom myCustom = userCustomRepository.findByEmail(email);

        if(myCustom == null) {
            throw new UsernameNotFoundException("User inexistant avec l'email : " + email);
        }
        return new User(myCustom.getEmail(), myCustom.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(myCustom.getRole().toString())))                                                                                                                                                                                                                               ;
    }
}
