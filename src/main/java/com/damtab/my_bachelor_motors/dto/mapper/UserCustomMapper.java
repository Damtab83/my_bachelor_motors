package com.damtab.my_bachelor_motors.dto.mapper;


import com.damtab.my_bachelor_motors.dto.response.UserCustomResponseDto;
import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserCustomMapper {

    private final UserCustomRepository userCustomRepository;
    private final OrderCarMapper orderCarMapper;

    public UserCustomResponseDto toResponseDto(UserCustom userCustom) {

        if (userCustom == null) {
            return null;
        }

        return new UserCustomResponseDto(
                userCustom.getId(),
                userCustom.getFirstname(),
                userCustom.getLastname(),
                userCustom.getEmail(),
                userCustom.getRole(),
                userCustom.getOrdersCars()== null
                        ? List.of()
                        : userCustom.getOrdersCars().stream()
                        .map(orderCarMapper::toResponseDto)
                        .toList()
        );
    }
}
