package com.damtab.my_bachelor_motors.serviceTest;

import com.damtab.my_bachelor_motors.entity.TestDriving;
import com.damtab.my_bachelor_motors.repository.TestDrivingRepository;
import com.damtab.my_bachelor_motors.service.TestDrivingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestDrivingServiceTest {

    @Mock
    private TestDrivingRepository testDrivingRepository;

    @InjectMocks
    private TestDrivingService testDrivingService;

    private TestDriving test;
    private TestDriving test2;
    private List<TestDriving> myTestList;

    @BeforeEach
    public void initialize() {
        test = new TestDriving();
        test.setTestDrivingId(44L);
        test.setTestDate(LocalDateTime.of(2026, 8, 19, 15, 30));
        test.setConfirmed(true);

        test2 = new TestDriving();
        test2.setTestDrivingId(55L);
        test2.setTestDate(LocalDateTime.of(2026, 8, 20, 15, 30));
        test2.setConfirmed(false);

        myTestList = new ArrayList<>();
        myTestList.add(test);
        myTestList.add(test2);
    }

    @Test
    public void getAllTestDriving_shouldReturnTestList() throws Exception {
        Mockito.when(testDrivingRepository.findAll()).thenReturn(myTestList);
        List<TestDriving> result = testDrivingService.getAllTestDriving();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(44L, result.get(0).getTestDrivingId());
        assertEquals(true, result.get(0).isConfirmed());
        assertEquals(LocalDateTime.of(2026, 8, 19, 15, 30), result.get(0).getTestDate());
        assertEquals(55L, result.get(1).getTestDrivingId());
        assertEquals(false, result.get(1).isConfirmed());
        assertEquals(LocalDateTime.of(2026, 8, 20, 15, 30), result.get(1).getTestDate());
    }

    @Test
    public void getTestDrivnigById_shouldReturnTest_whenTestExist() throws Exception {
        Mockito.when(testDrivingRepository.findById(44L)).thenReturn(Optional.of(test));

        TestDriving result = testDrivingService.getTestDriving(44L);
        assertNotNull(result);
        assertEquals(44L, result.getTestDrivingId());
        assertEquals(true, result.isConfirmed());
        assertEquals(LocalDateTime.of(2026, 8, 19, 15, 30), result.getTestDate());
    }

    @Test
    public void createTestDriving_shouldReturn201() throws Exception {
        testDrivingService.createTestDriving(test2);
        Mockito.verify(testDrivingRepository).save(test2);
    }

    @Test
    public void deleteTestDriving_shouldModifyTest_whenTestExist() throws Exception {
        Mockito.when(testDrivingRepository.existsById(44L)).thenReturn(true);
        Boolean result = testDrivingService.deleteTestDriving(44L);
        assertTrue(result);
        Mockito.verify(testDrivingRepository).deleteById(44L);
    }

    @Test
    public void updateTestDriving_shouldModifyTest_whenTestExist() throws Exception {
        TestDriving newTest = new TestDriving();
        newTest.setTestDate(LocalDateTime.of(2026, 10, 5, 10,00));
        newTest.setConfirmed(true);

        Mockito.when(testDrivingRepository.findById(55L)).thenReturn(Optional.of(test2));

        testDrivingService.updateTestDriving(55L, newTest);
        assertEquals(55L, test2.getTestDrivingId());
        assertEquals(true, test2.isConfirmed());
        assertEquals(LocalDateTime.of(2026, 10, 5, 10, 00), test2.getTestDate());

        Mockito.verify(testDrivingRepository).findById(55L);
        Mockito.verify(testDrivingRepository).save(test2);
    }

}
