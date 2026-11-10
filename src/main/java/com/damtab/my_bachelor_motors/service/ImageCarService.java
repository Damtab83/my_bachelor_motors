package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.Car;
import com.damtab.my_bachelor_motors.entity.ImageCar;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.CarRepository;
import com.damtab.my_bachelor_motors.repository.ImageCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageCarService {

    //Create CRUD for Imager-Car
    //Update Image-Car not Exist, for modify image delete it and create an other image
    private final ImageCarRepository imageCarRepository;
    private final CarRepository carRepository;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //Read List of Images
    public List<ImageCar> getAllImageCars() {
        List<ImageCar> images = imageCarRepository.findAll();

        if (images.isEmpty()) {
            throw new ResourceNotFoundException("Aucunes images trouvées");
        }

        return images;
    }

    //Read Image By Id
    public ImageCar getImageCarById(Long id) {

        return imageCarRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aucune image trouvée"));
    }

    //Create Image-Car
    public void createImageCar(ImageCar newImage) {
        imageCarRepository.save(newImage);
    }

    //Delete Image-Car
    public boolean deleteImageCar(Long id) {
        boolean toDelete = imageCarRepository.existsById(id);
        if (toDelete) {
            imageCarRepository.deleteById(id);
        }
        return toDelete;
    }

    // Upload and save an image associated with a car
    public ImageCar saveImage(MultipartFile file, Long carId) throws IOException {

        // Vérifier que le fichier n'est pas vide
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier image est vide");
        }

        // Vérifier le format de l'image
        String contentType = file.getContentType();

        if (contentType == null ||
                (!contentType.equals("image/jpeg") &&
                        !contentType.equals("image/png") &&
                        !contentType.equals("image/webp"))) {
            throw new IllegalArgumentException("Format d'image non autorisé");
        }

        // Vérifier que la voiture existe
        Car car = carRepository.findById(carId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aucune voiture trouvée avec l'identifiant : " + carId));

        // Créer l'entité ImageCar
        ImageCar imageCar = new ImageCar();

        imageCar.setName(file.getOriginalFilename());
        imageCar.setSize(file.getSize());
        imageCar.setContentType(contentType);
        imageCar.setImageData(file.getBytes());
        imageCar.setCar(car);

        return imageCarRepository.save(imageCar);
    }
}