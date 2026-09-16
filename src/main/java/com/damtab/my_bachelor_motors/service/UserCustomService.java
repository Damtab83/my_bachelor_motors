package com.damtab.my_bachelor_motors.service;

import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.exception.ResourceNotFoundException;
import com.damtab.my_bachelor_motors.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCustomService {

    //Get and delete Users, only for Admin
    private final UserCustomRepository userCustomRepository;

    //Get List of Users
    public List<UserCustom> getAllUserCustom() {
        List<UserCustom> userCustomers = userCustomRepository.findAll();
        if(userCustomers.isEmpty()) {
            throw new ResourceNotFoundException("Aucuns clients trouvés");
        }
        return userCustomers;
    }

    //Get User By Id
    public UserCustom getUserCustomById(Long id) {
        return userCustomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aucun client trouvé"));

    }

    //Delete User
    public boolean deleteUserCustom (Long id) {
        Boolean toDelete = userCustomRepository.existsById(id);
        if(toDelete) {
            userCustomRepository.deleteById(id);
        }
        return toDelete;
    }
}
