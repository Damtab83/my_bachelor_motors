package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.OldCar;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.OldCarRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OldCarService {

    private static final Logger log =
            LoggerFactory.getLogger(OldCarService.class);

    //CRUD of Old-Car
    //Update Old-Car not exist, not change Old-Car will be change with Car
    private final OldCarRepository oldCarRepository;

    //Get List of Old-Car
    public List<OldCar> getAllOldCars() {
        List<OldCar> oldCars = oldCarRepository.findAll();
        log.debug("Nombre d'anciennes voitures récupérées : {}", oldCars.size());
        if(oldCars.isEmpty()) {
            throw new ResourceNotFoundException("Aucunes anciennes voitures trouvées");
        }
        return oldCars;
    }

    //Get Old-Car By Id
    public Optional<OldCar> getOldCarById(Long id) {
        Optional<OldCar> oldCar = oldCarRepository.findById(id);

        if(oldCar.isEmpty()) {
            throw new ResourceNotFoundException("Aucune ancienne voiture trouvée");
        }
        return oldCar;
    }

    //Create Old-Car

    public OldCar createOldCar(OldCar newOldCar) {

        OldCar oldCar = oldCarRepository.save(newOldCar);
        log.info("Ancienne voiture créée avec succès. Identifiant : {}", oldCar.getOldCarId());
        return oldCar;
    }

    //Delete Old-Car
    public boolean deleteOldCar(Long id) {
        boolean toDelete = oldCarRepository.existsById(id);
        if(toDelete) {
            oldCarRepository.deleteById(id);
            log.info("Ancienne voiture supprimée avec succès. Identifiant : {}", id);
        }
        return toDelete;
    }
}
