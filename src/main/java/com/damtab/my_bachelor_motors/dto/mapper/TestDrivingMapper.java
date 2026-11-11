package com.damtab.my_bachelor_motors.dto.mapper;

import com.damtab.my_bachelor_motors.dto.request.TestDrivingRequestDto;
import com.damtab.my_bachelor_motors.dto.response.TestDrivingResponseDto;
import com.damtab.my_bachelor_motors.entity.TestDriving;
import org.springframework.stereotype.Component;

@Component
public class TestDrivingMapper {


    public TestDriving toEntity(TestDrivingRequestDto dto) {
        TestDriving test = new TestDriving();
        test.setTestDate(dto.testDate());
        test.setConfirmed(dto.confirmed());
        return test;
    }

    public TestDrivingResponseDto toResponseDto(TestDriving test) {
        return new TestDrivingResponseDto(
                test.getTestDrivingId(),
                test.getTestDate(),
                test.isConfirmed()
        );
    }
}
