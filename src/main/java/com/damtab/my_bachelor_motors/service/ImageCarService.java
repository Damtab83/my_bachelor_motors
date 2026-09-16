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

    //Create CRUD for Imager-Car
    //Update Image-Car not Exist, for modify image delete it and create an other image
    private final ImageCarRepository imageCarRepository;

    //Read List of Images
    public List<ImageCar> getAllImageCars() {
        List<ImageCar> images = imageCarRepository.findAll();

        if(images.isEmpty()) {
            throw new ResourceNotFoundException ("Aucunes images trouvées");
        }

        return images;
    }

    //Read Image By Id
    public ImageCar getImageCarById(Long id) {

        return imageCarRepository.findById(id)
                .orElseThrow(()->
           new ResourceNotFoundException ("Aucune image trouvée"));
    }

    //Create Image-Car
    public void createImageCar(ImageCar newImage) {
        imageCarRepository.save(newImage);
    }

    //Delete Image-Car
    public boolean deleteImageCar(Long id) {
        Boolean toDelete = imageCarRepository.existsById(id);
        if(toDelete) {
            imageCarRepository.deleteById(id);
        }
        return toDelete;
    }
    
}
