package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.dto.mapper.TestDrivingMapper;
import com.damtab.my_bachelor_motors.dto.request.TestDrivingRequestDto;
import com.damtab.my_bachelor_motors.dto.response.TestDrivingResponseDto;
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
    @Autowired
    private TestDrivingMapper testDrivingMapper;

    public TestDrivingController(TestDrivingService testDrivingService, TestDrivingMapper testDrivingMapper) {
        this.testDrivingService = testDrivingService;
        this.testDrivingMapper = testDrivingMapper;
    }

    //Get List of TestDriving
    @GetMapping
    public ResponseEntity<List<TestDrivingResponseDto>> getAllTestDriving () {
        List<TestDrivingResponseDto> myListTestDriving = testDrivingService.getAllTestDriving()
                .stream()
                .map(testDrivingMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(myListTestDriving);
    }

    //Get TestDriving By Id
    @GetMapping("/{id}")
    public ResponseEntity<TestDrivingResponseDto>getTestDrivingById(@PathVariable Long id) {
        TestDriving myTestDriving = testDrivingService.getTestDriving(id);
        if (myTestDriving == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(testDrivingMapper.toResponseDto(myTestDriving));
    }

    //Create TestDriving
    @PostMapping
    public ResponseEntity<TestDrivingResponseDto> createTestDriving (@RequestBody TestDrivingRequestDto requestDto) {
        TestDriving newTest = testDrivingMapper.toEntity(requestDto);
        TestDriving savedTest = testDrivingService.createTestDriving(newTest);
        return ResponseEntity.status(HttpStatus.CREATED).body(testDrivingMapper.toResponseDto(savedTest));
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
    public ResponseEntity<TestDrivingResponseDto> updateTestDriving (@PathVariable Long id, @RequestBody TestDrivingRequestDto newRequestDto) {
        TestDriving testToUpdate = testDrivingMapper.toEntity(newRequestDto);
        TestDriving updatedTest = testDrivingService.updateTestDriving(id, testToUpdate);
        return ResponseEntity.ok(testDrivingMapper.toResponseDto(updatedTest));
    }
}
