package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.OldCar;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.OldCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OldCarService {

    private final OldCarRepository oldCarRepository;

    public List<OldCar> getAllOldCars() {
        List<OldCar> oldCars = oldCarRepository.findAll();
        if(oldCars.isEmpty()) {
            throw new ResourceNotFoundException("Aucunes anciennes voitures trouvées");
        }
        return oldCars;
    }

    public Optional<OldCar> getOldCarById(Long id) {
        Optional<OldCar> oldCar = oldCarRepository.findById(id);

        if(oldCar.isEmpty()) {
            throw new ResourceNotFoundException("Aucune ancienne voiture trouvée");
        }
        return oldCar;
    }

    public void createOldCar(OldCar newOldCar) {
        oldCarRepository.save(newOldCar);
    }

    public boolean deleteOldCar(Long id) {
        Boolean toDelete = oldCarRepository.existsById(id);
        if(toDelete) {
            oldCarRepository.deleteById(id);
        }
        return toDelete;
    }
}
