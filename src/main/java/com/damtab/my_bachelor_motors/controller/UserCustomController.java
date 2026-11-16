package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.service.UserCustomDetailsService;
import com.damtab.my_bachelor_motors.service.UserCustomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_USER_CUSTOM)
public class UserCustomController {

    private static final Logger log =
            LoggerFactory.getLogger(UserCustomController.class);


    //Differents routing for...
    @Autowired
    private UserCustomService userCustomService;

    //Get List of Users
    @GetMapping
    public ResponseEntity<Object> getAllUserCustom () {
        List<UserCustom> myListUserCustom = userCustomService.getAllUserCustom();
        if(myListUserCustom == null || myListUserCustom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("Requête GET terminée : {} client(s) récupé(s)");
        return ResponseEntity.status(HttpStatus.OK).body(myListUserCustom);
    }

    //Get User By Id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserCutomById(@PathVariable Long id) {
        UserCustom myUserCustom = userCustomService.getUserCustomById(id);
        log.info("Requête GET reçue : récupération du client ID={}", id);
        return myUserCustom == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myUserCustom);
    }

    //Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserCustom(@PathVariable Long id) {
        boolean isDelete = userCustomService.deleteUserCustom(id);
        log.info("Requête DELETE terminée : client ID={} supprimé", id);
        return isDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
