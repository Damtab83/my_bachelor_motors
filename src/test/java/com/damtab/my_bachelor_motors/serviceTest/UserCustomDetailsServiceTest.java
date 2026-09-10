package com.damtab.my_bachelor_motors.serviceTest;

import com.damtab.my_bachelor_motors.entity.Role;
import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.repository.UserCustomRepository;
import com.damtab.my_bachelor_motors.service.UserCustomDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserCustomDetailsServiceTest {

    @Mock
    private UserCustomRepository userCustomRepository;

    @InjectMocks
    private UserCustomDetailsService userCustomDetailsService;

    private UserCustom userTest;

    @BeforeEach
    public void initialize() {
        userTest = new UserCustom();
        userTest.setId(11L);
        userTest.setFirstname("John");
        userTest.setLastname("Doe");
        userTest.setEmail("john.doe@test.fr");
        userTest.setPassword("john1234");
        userTest.setRole(Role.ROLE_CUSTOM);
    }

    @Test
    public void loadUserByUsername_shouldReturnUserDetails_whenUserExist() throws Exception {
        Mockito.when(userCustomRepository.findByEmail("john.doe@test.fr")).thenReturn(userTest);

        UserDetails result = userCustomDetailsService.loadUserByUsername("john.doe@test.fr");
        assertNotNull(result);
        assertEquals("john.doe@test.fr", result.getUsername());
        assertEquals("john1234", result.getPassword());

        assertTrue(result.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_CUSTOM")));

        Mockito.verify(userCustomRepository).findByEmail("john.doe@test.fr");
    }

    @Test
    public void loadByUserName_shouldReturnFalse_whenUserNotExist() throws Exception {
        Mockito.when(userCustomRepository.findByEmail("john.doe@test.fr")).thenReturn(null);
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userCustomDetailsService.loadUserByUsername("john.doe@test.fr")
        );

        assertEquals("User inexistant avec l'email : john.doe@test.fr", exception.getMessage());

        Mockito.verify(userCustomRepository).findByEmail("john.doe@test.fr");
    }
}
