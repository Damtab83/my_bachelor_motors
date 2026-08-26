package com.damtab.my_bachelor_motors.serviceTest;

import com.damtab.my_bachelor_motors.entity.Role;
import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.repository.UserCustomRepository;
import com.damtab.my_bachelor_motors.service.UserCustomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserCustomServiceTest {

    @Mock
    private UserCustomRepository userCustomRepository;

    @InjectMocks
    private UserCustomService userCustomService;

    private UserCustom user1;
    private UserCustom user2;
    private List<UserCustom> myListUser;

    @BeforeEach
    public void initialize() {
        user1 = new UserCustom();
        user1.setId(11L);
        user1.setFirstname("John");
        user1.setLastname("Doe");
        user1.setEmail("john.doe@test.fr");
        user1.setPassword("john1234");
        user1.setRole(Role.ROLE_ADMIN);

        user2 = new UserCustom();
        user2.setId(22L);
        user2.setFirstname("Jane");
        user2.setLastname("Tarzan");
        user2.setEmail("jane.tarzan@test.fr");
        user2.setPassword("jane1234");
        user2.setRole(Role.ROLE_CUSTOM);

        myListUser = new ArrayList<>();
        myListUser.add(user1);
        myListUser.add(user2);
    }

    @Test
    public void getAllUserCustom_shouldReturnList() throws Exception {
        Mockito.when(userCustomRepository.findAll()).thenReturn(myListUser);
        List<UserCustom> result = userCustomService.getAllUserCustom();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(11L, result.get(0).getId());
        assertEquals("John", result.get(0).getFirstname());
        assertEquals("Doe", result.get(0).getLastname());
        assertEquals("john.doe@test.fr", result.get(0).getEmail());
        assertEquals("john1234",result.get(0).getPassword());
        assertEquals(Role.ROLE_ADMIN, result.get(0).getRole());
        assertEquals(22L, result.get(1).getId());
        assertEquals("Jane", result.get(1).getFirstname());
        assertEquals("Tarzan", result.get(1).getLastname());
        assertEquals("jane.tarzan@test.fr", result.get(1).getEmail());
        assertEquals("jane1234",result.get(1).getPassword());
        assertEquals(Role.ROLE_CUSTOM, result.get(1).getRole());
    }


    @Test
    public void getUserCustomById_shouldReturnUser_whenUserExist() throws Exception {
        Mockito.when(userCustomRepository.findById(11L)).thenReturn(Optional.of(user1));
        UserCustom result = userCustomService.getUserCustomById(11L);
        assertEquals(11L, result.getId());
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals("john.doe@test.fr", result.getEmail());
        assertEquals("john1234",result.getPassword());
        assertEquals(Role.ROLE_ADMIN, result.getRole());
    }

    @Test
    public void deleteUserCustomById_whenUserCustomExist() throws Exception {
        Mockito.when(userCustomRepository.existsById(11L)).thenReturn(true);
        Boolean result = userCustomService.deleteUserCustom(11L);
        assertTrue(result);
        Mockito.verify(userCustomRepository).deleteById(11L);

    }
}
