package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.entity.TestDriving;
import com.damtab.my_bachelor_motors.service.TestDrivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_TESTDRIVING)
public class TestDrivingController {

    //Differents routing for...
    @Autowired
    private TestDrivingService testDrivingService;

    //Get List of TestDriving
    @GetMapping
    public ResponseEntity<Object> getAllTestDriving () {
        List<TestDriving> myListTestDriving = testDrivingService.getAllTestDriving();
        if(myListTestDriving == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(myListTestDriving);
    }

    //Get TestDriving By Id
    @GetMapping("/{id}")
    public ResponseEntity<Object>getTestDrivingById(@PathVariable Long id) {
        TestDriving myTestDriving = testDrivingService.getTestDriving(id);
        return myTestDriving == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myTestDriving);
    }

    //Create TestDriving
    @PostMapping
    public ResponseEntity<Object> createTestDriving (@RequestBody TestDriving myTestDriving) {
        testDrivingService.createTestDriving(myTestDriving);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //Delete TestDriving
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTestDriving (@PathVariable Long id) {
        Boolean isDelete = testDrivingService.deleteTestDriving(id);
        return isDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //Update TestDriving
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTestDriving (@PathVariable Long id, @RequestBody TestDriving newTestDriving) {
        testDrivingService.updateTestDriving(id, newTestDriving);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
