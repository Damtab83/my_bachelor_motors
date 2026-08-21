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

    private final TestDrivingRepository testDrivingRepository;

    public List<TestDriving> getAllTestDriving () {
        List<TestDriving> testDrivingList = testDrivingRepository.findAll();
        if(testDrivingList.isEmpty()) {
            throw new ResourceNotFoundException("Aucuns rendez vous d'essai trouvé");
        }
        return testDrivingList;
    }
    public TestDriving getTestDriving(Long id) {
        Optional<TestDriving> testDrive = testDrivingRepository.findById(id);
        return testDrive.orElse(null);
    }

    public void createTestDriving(TestDriving newTest) {
        testDrivingRepository.save(newTest);
    }

    public  boolean deleteTestDriving(Long id) {
        Boolean toDelete = testDrivingRepository.existsById(id);
        if(toDelete) {
            testDrivingRepository.deleteById(id);
        }
        return toDelete;
    }

    public void updateTestDriving(Long id, TestDriving newTestDriving) {
        TestDriving oldTestDriving = this.getTestDriving(id);
        if(oldTestDriving != null) {
            oldTestDriving.setTestDate(newTestDriving.getTestDate());
            oldTestDriving.setConfirmed(newTestDriving.isConfirmed());
            testDrivingRepository.save(oldTestDriving);
        }
    }
}
