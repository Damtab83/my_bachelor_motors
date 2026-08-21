package com.damtab.my_bachelor_motors.controllerTest;

import com.damtab.my_bachelor_motors.configuration.jwt.JwtUtils;
import com.damtab.my_bachelor_motors.controller.ApiRegistration;
import com.damtab.my_bachelor_motors.controller.TestDrivingController;
import com.damtab.my_bachelor_motors.entity.TestDriving;
import com.damtab.my_bachelor_motors.service.TestDrivingService;
import com.damtab.my_bachelor_motors.service.UserCustomDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestDrivingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestDrivingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TestDrivingService testDrivingService;

    @MockitoBean
    private UserCustomDetailsService userCustomDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    private TestDriving test1;
    private TestDriving test2;
    private List<TestDriving> myTestList;

    @BeforeEach
    public void initialize() {
        test1 = new TestDriving();
        test1.setTestDrivingId(30L);
        test1.setConfirmed(true);
        test1.setTestDate(LocalDateTime.of(2026, 8,22,12,30));

        test2 = new TestDriving();
        test2.setTestDrivingId(40L);
        test2.setConfirmed(false);
        test2.setTestDate(LocalDateTime.of(2026, 10,30,16,30));

        myTestList = new ArrayList<>();
        myTestList.add(test1);
        myTestList.add(test2);
    }

    @Test
    public void getAllTestDriving_shouldReturnTestList() throws Exception {
        Mockito.when(testDrivingService.getAllTestDriving()).thenReturn(myTestList);
        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_TESTDRIVING))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].testDrivingId").value(30L))
                .andExpect(jsonPath("$[0].confirmed").value(true))
                .andExpect(jsonPath("$[0].testDate").value("2026-08-22T12:30:00"))
                .andExpect(jsonPath("$[1].testDrivingId").value(40L))
                .andExpect(jsonPath("$[1].confirmed").value(false))
                .andExpect(jsonPath("$[1].testDate").value("2026-10-30T16:30:00"));
    }

    @Test
    public void getTestDrivingById_shouldReturnTest_whenExist() throws Exception {
        Mockito.when(testDrivingService.getTestDriving(30L)).thenReturn(test1);
        mockMvc.perform(get(ApiRegistration.REST_API + ApiRegistration.REST_TESTDRIVING + "/30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testDrivingId").value(30L))
                .andExpect(jsonPath("$.confirmed").value(true))
                .andExpect(jsonPath("$.testDate").value("2026-08-22T12:30:00"));
    }

    @Test
    public void createTestDriving_shouldReturn201() throws Exception {
        mockMvc.perform(post(ApiRegistration.REST_API + ApiRegistration.REST_TESTDRIVING)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(test2)))
                .andExpect(status().isCreated());

        Mockito.verify(testDrivingService).createTestDriving(Mockito.any(TestDriving.class));
    }

    @Test
    public void deleteTestDrivingById_whenTestExist() throws Exception {
        Mockito.when(testDrivingService.deleteTestDriving(30L)).thenReturn(true);
        mockMvc.perform(delete(ApiRegistration.REST_API + ApiRegistration.REST_TESTDRIVING + "/30"))
                .andExpect(status().isOk());
    }

    @Test
    public void updatedTestDrivingById_shouldDeleteTest_whenTestExist()throws Exception {
        TestDriving newTest = new TestDriving();
        newTest.setTestDate(LocalDateTime.of(2026, 12,12,12,12));
        mockMvc.perform(put(ApiRegistration.REST_API + ApiRegistration.REST_TESTDRIVING + "/40")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newTest)))
                .andExpect(status().isOk());
        Mockito.verify(testDrivingService).updateTestDriving(Mockito.eq(40L), Mockito.any(TestDriving.class));
    }





}
