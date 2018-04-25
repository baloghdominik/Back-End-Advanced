package com.greenfoxacademy.baloghdominik.authentication.controllers;

/**
 * Created by baloghdominik on 2018. 04. 25..
 */

import com.greenfoxacademy.baloghdominik.authentication.models.JsonResponse;
import com.greenfoxacademy.baloghdominik.authentication.models.UserModel;
import com.greenfoxacademy.baloghdominik.authentication.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationUserRestController {

    @Autowired
    UserModelRepository userModelRepository;

    @PostMapping(value = {"/sign_up"})
    public ResponseEntity register(@RequestBody(required = true) UserModel newUserModel) {
        if (newUserModel != null) {
            userModelRepository.save(newUserModel);
            return new ResponseEntity(new JsonResponse("User is registered"), HttpStatus.OK);
        }
        else {
            return new ResponseEntity(new JsonResponse("Error"), HttpStatus.BAD_REQUEST);
        }
    }
}
