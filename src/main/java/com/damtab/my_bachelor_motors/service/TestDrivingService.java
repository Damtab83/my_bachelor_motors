package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.TestDriving;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.TestDrivingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TestDrivingService {

    //CRUD for TestDriving
    private final TestDrivingRepository testDrivingRepository;

    //Get List of TestDriving
    public List<TestDriving> getAllTestDriving () {
        List<TestDriving> testDrivingList = testDrivingRepository.findAll();
        if(testDrivingList.isEmpty()) {
            throw new ResourceNotFoundException("Aucuns rendez vous d'essai trouvé");
        }
        return testDrivingList;
    }

    //Get TestDriving By Id
    public TestDriving getTestDriving(Long id) {
        return testDrivingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Rendez-vous d'essai introuvable"
                        ));

    }

    //Create TestDriving
    public void createTestDriving(TestDriving newTest) {
        testDrivingRepository.save(newTest);
    }

    //Delete TestDriving Car
    public  boolean deleteTestDriving(Long id) {
        Boolean toDelete = testDrivingRepository.existsById(id);
        if(toDelete) {
            testDrivingRepository.deleteById(id);
        }
        return toDelete;
    }

    //Update for change date and time for Testing Driving Car
    public void updateTestDriving(Long id, TestDriving newTestDriving) {
        TestDriving oldTestDriving = this.getTestDriving(id);
        if(oldTestDriving != null) {
            oldTestDriving.setTestDate(newTestDriving.getTestDate());
            oldTestDriving.setConfirmed(newTestDriving.isConfirmed());
            testDrivingRepository.save(oldTestDriving);
        }
    }
}
