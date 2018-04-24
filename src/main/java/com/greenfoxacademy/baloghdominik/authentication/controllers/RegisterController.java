package com.greenfoxacademy.baloghdominik.authentication.controllers;

import com.greenfoxacademy.baloghdominik.authentication.models.UserModel;
import com.greenfoxacademy.baloghdominik.authentication.repositories.UserModelRepository;
import com.greenfoxacademy.baloghdominik.authentication.services.RegisterValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by baloghdominik on 2018. 04. 24..
 */

@Controller
public class RegisterController {

    private RegisterValidation registerValidation;
    private UserModelRepository userModelRepository;

    @Autowired
    public RegisterController(RegisterValidation registerValidation, UserModelRepository userModelRepository){
        this.registerValidation = registerValidation;
        this.userModelRepository = userModelRepository;
    }

    @GetMapping(value = "/register")
    public String registerPage(Model model){
        model.addAttribute("error", false);
        model.addAttribute("errorMessage", "");
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerNewUser(@ModelAttribute(value="username") String username, @ModelAttribute(value = "password") String password,
                                  @ModelAttribute(value = "passwordConfirmation") String passwordConfirmation, Model model){

        if (registerValidation.isFieldsNotEmpty(username, password, passwordConfirmation)){
            if (registerValidation.isFieldsContainsOnlyNormalCharacters(username, password, passwordConfirmation)){
                if (registerValidation.isUsernameLongEnough(username, 4)){
                    if (registerValidation.isPasswordLongEnough(password, 4)){
                        if (registerValidation.isPasswordMatching(password, passwordConfirmation)){
                            if (registerValidation.isPasswordStrong(password)){
                                if (registerValidation.isUsernameUnique(username)){
                                    userModelRepository.save(new UserModel(username, password));
                                    model.addAttribute("error", false);
                                    model.addAttribute( "success", true);
                                    model.addAttribute("errorMessage", "You registered successfully! Please sign in!");
                                    return "login";
                                } else {
                                    model.addAttribute("error", true);
                                    model.addAttribute("errorMessage",
                                            "The given username is already in use!");
                                }
                            } else {
                                model.addAttribute("error", true);
                                model.addAttribute("errorMessage",
                                        "The given password is not strong enough!");
                            }
                        } else {
                            model.addAttribute("error", true);
                            model.addAttribute("errorMessage",
                                    "The given passwords are not matching!");
                        }
                    } else {
                        model.addAttribute("error", true);
                        model.addAttribute("errorMessage",
                                "The given password is too short, or too long. " +
                                        "Please try a different one!");
                    }
                } else {
                    model.addAttribute("error", true);
                    model.addAttribute("errorMessage",
                            "The given username is too short, or too long. " +
                                    "Please try a different one!");
                }
            } else {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage",
                        "Special characters are not accepted! " +
                        "Accepted characters: (A-Z, a-z, 0-9, _)");
            }
        } else {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage",
                    "Please fill every field!");
        }

        return "register";
    }
}
