package com.damtab.my_bachelor_motors.controllerTest;

import com.damtab.my_bachelor_motors.configuration.jwt.JwtUtils;
import com.damtab.my_bachelor_motors.controller.ApiRegistration;
import com.damtab.my_bachelor_motors.controller.UserCustomController;
import com.damtab.my_bachelor_motors.entity.Role;
import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.service.UserCustomDetailsService;
import com.damtab.my_bachelor_motors.service.UserCustomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserCustomController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserCustomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserCustomService userCustomService;

    @MockitoBean
    private UserCustomDetailsService userCustomDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    private UserCustom user1;
    private UserCustom user2;
    private List<UserCustom> myUserList;

    @BeforeEach
    public void initialize() {
        user1 = new UserCustom();
        user1.setId(10L);
        user1.setFirstname("John");
        user1.setLastname("Doe");
        user1.setEmail("john.doe@test.fr");
        user1.setPassword("john1234");
        user1.setRole(Role.ROLE_ADMIN);

        user2 = new UserCustom();
        user2.setId(20L);
        user2.setFirstname("Jane");
        user2.setLastname("Tarzan");
        user2.setEmail("jane.tarzan@test.fr");
        user2.setPassword("jane1234");
        user2.setRole(Role.ROLE_CUSTOM);

        myUserList = new ArrayList<>();
        myUserList.add(user1);
        myUserList.add(user2);
    }

    @Test
    public void getAllUserCustom_shouldReturnUserCustomList() throws Exception {
        Mockito.when(userCustomService.getAllUserCustom()).thenReturn(myUserList);
        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_USER_CUSTOM))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].firstname").value("John"))
                .andExpect(jsonPath("$[0].lastname").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@test.fr"))
                .andExpect(jsonPath("$[1].id").value(20))
                .andExpect(jsonPath("$[1].firstname").value("Jane"))
                .andExpect(jsonPath("$[1].lastname").value("Tarzan"))
                .andExpect(jsonPath("$[1].email").value("jane.tarzan@test.fr"));
    }

    @Test
    public void getUserCustomById_shouldReturnUser_whenUserExist() throws Exception {
        Mockito.when(userCustomService.getUserCustomById(10L)).thenReturn(user1);
        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_USER_CUSTOM + "/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@test.fr"));

    }

    @Test
    public void deleteUserCustom_shouldDeleteUser_whenuserExist() throws Exception {
        Mockito.when(userCustomService.deleteUserCustom(10L)).thenReturn(true);
        mockMvc.perform(delete(ApiRegistration.REST_API + ApiRegistration.REST_USER_CUSTOM + "/10"))
                .andExpect(status().isOk());
    }
}
