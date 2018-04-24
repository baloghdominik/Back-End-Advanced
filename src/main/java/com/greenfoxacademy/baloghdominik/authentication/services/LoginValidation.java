package com.greenfoxacademy.baloghdominik.authentication.services;

import com.greenfoxacademy.baloghdominik.authentication.models.UserModel;
import com.greenfoxacademy.baloghdominik.authentication.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by baloghdominik on 2018. 04. 24..
 */

@Service
public class LoginValidation {

    private UserModelRepository userModelRepository;
    private Encryption encryption;

    @Autowired
    public LoginValidation(UserModelRepository userModelRepository, Encryption encryption){
        this.userModelRepository = userModelRepository;
        this.encryption = encryption;
    }

    public boolean isUserExists(String username){
        return userModelRepository.findAllByUsername(username).size() == 1;
    }

    public boolean isPasswordMatchToUsername(String username, String password){
        UserModel user = userModelRepository.findOneByUsername(username);
        return user.getPassword().equals(password);
    }
}
