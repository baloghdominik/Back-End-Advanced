package com.greenfoxacademy.baloghdominik.authentication.services;

import com.greenfoxacademy.baloghdominik.authentication.models.UserModel;
import com.greenfoxacademy.baloghdominik.authentication.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by baloghdominik on 2018. 04. 24..
 */

@Service
public class RegisterValidation {

    private UserModelRepository userModelRepository;

    @Autowired
    public RegisterValidation(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    public boolean isUsernameLongEnough(String username, Integer minUsernameLength){
        return username.length() >= minUsernameLength && username.length() <= 16;
    }

    public boolean isUsernameUnique(String username){
        List<UserModel> users = userModelRepository.findAllByUsername(username);
        return users.isEmpty();
    }

    public boolean isPasswordMatching(String password, String passwordConfirmation){
        return password.equals(passwordConfirmation);
    }

    public boolean isFieldsNotEmpty(String username, String password, String passwordConfirmation){
        return username != null && password != null && passwordConfirmation != null;
    }

    public boolean isPasswordLongEnough(String password, Integer minPasswordLength){
        return password.length() >= minPasswordLength && password.length() <= 32;
    }

    public boolean isFieldsContainsOnlyNormalCharacters(String username, String password, String passwordConfirmation){
        return username.matches("[a-zA-Z_0-9]*")
                && password.matches("[a-zA-Z_0-9]*")
                && passwordConfirmation.matches("[a-zA-Z_0-9]*");
    }

    public boolean isPasswordStrong(String password){
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (int i = 0; i < password.length(); i++) {
            char x = password.charAt(i);
            if (Character.isLetter(x)) {
                hasLetter = true;
            }
            else if (Character.isDigit(x)) {
                hasDigit = true;
            }
            if(hasLetter && hasDigit){
                break;
            }
        }
        return hasLetter && hasDigit;
    }
}
