package com.damtab.my_bachelor_motors.controller;

import com.damtab.my_bachelor_motors.entity.UserCustom;
import com.damtab.my_bachelor_motors.service.UserCustomDetailsService;
import com.damtab.my_bachelor_motors.service.UserCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiRegistration.REST_API + ApiRegistration.REST_USER_CUSTOM)
public class UserCustomController {


    @Autowired
    private UserCustomService userCustomService;

    @GetMapping
    public ResponseEntity<Object> getAllUserCustom () {
        List<UserCustom> myListUserCustom = userCustomService.getAllUserCustom();
        return ResponseEntity.status(HttpStatus.OK).body(myListUserCustom);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserCutomById(@PathVariable Long id) {
        UserCustom myUserCustom = userCustomService.getUserCustomById(id);
        return myUserCustom == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(myUserCustom);
    }

    @DeleteMapping("/id}")
    public ResponseEntity<Object> deleteUserCustom(@PathVariable Long id) {
        Boolean isDelete = userCustomService.deleteUserCustom(id);
        return isDelete ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
