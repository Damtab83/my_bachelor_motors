package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.ImageCar;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.ImageCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageCarService {

    private final ImageCarRepository imageCarRepository;

    public List<ImageCar> getAllImageCars() {
        List<ImageCar> images = imageCarRepository.findAll();

        if(images.isEmpty()) {
            throw new ResourceNotFoundException ("Aucunes images trouvées");
        }

        return images;
    }

    public Optional<ImageCar> getImageCarById(Long id) {

        Optional<ImageCar> image = imageCarRepository.findById(id);

        if(image.isEmpty()) {
            throw new ResourceNotFoundException ("Aucune image trouvée");
        }
        return image;

    }

    public void createImageCar(ImageCar newImage) {
        imageCarRepository.save(newImage);
    }

    public boolean deleteImageCar(Long id) {
        Boolean toDelete = imageCarRepository.existsById(id);
        if(toDelete) {
            imageCarRepository.deleteById(id);
        }
        return toDelete;
    }
    
}
