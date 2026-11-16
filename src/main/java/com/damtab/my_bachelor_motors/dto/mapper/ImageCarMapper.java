package com.damtab.my_bachelor_motors.dto.mapper;

import com.damtab.my_bachelor_motors.dto.request.ImageCarRequestDto;
import com.damtab.my_bachelor_motors.dto.response.ImageCarResponseDto;
import com.damtab.my_bachelor_motors.entity.ImageCar;
import org.springframework.stereotype.Component;

@Component
public class ImageCarMapper {

    public ImageCar  toEntity(ImageCarRequestDto dto) {
        ImageCar imageCar = new ImageCar();

        imageCar.setName(dto.name());
        imageCar.setSize(dto.size());
        imageCar.setContentType(dto.contentType());
        imageCar.setImageData(dto.imageData());

        return imageCar;
    }

    public ImageCarResponseDto toResponseDto(ImageCar imageCar) {
        return new ImageCarResponseDto(
                imageCar.getImageCarId(),
                imageCar.getName(),
                imageCar.getSize(),
                imageCar.getContentType(),
                imageCar.getImageData()
        );
    }
}
