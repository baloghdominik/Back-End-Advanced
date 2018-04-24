package com.greenfoxacademy.baloghdominik.authentication.controllers;

import com.greenfoxacademy.baloghdominik.authentication.repositories.UserModelRepository;
import com.greenfoxacademy.baloghdominik.authentication.services.Encryption;
import com.greenfoxacademy.baloghdominik.authentication.services.LoginValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by baloghdominik on 2018. 04. 24..
 */

@Controller
public class LoginController {

    private UserModelRepository userModelRepository;
    private LoginValidation loginValidation;
    private Encryption encryption;

    public LoginController(UserModelRepository userModelRepository,
                           LoginValidation loginValidation,
                           Encryption encryption){
        this.userModelRepository = userModelRepository;
        this.loginValidation = loginValidation;
        this.encryption = encryption;
    }

    @GetMapping(value = "/login")
    public String loginPage(Model model){
        model.addAttribute( "success", false);
        model.addAttribute("error", false);
        model.addAttribute("errorMessage", "");
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginUser(@ModelAttribute(value="username") String username,
                            @ModelAttribute(value = "password") String password, Model model){

            if (loginValidation.isUserExists(username)) {
                if (loginValidation.isPasswordMatchToUsername(username, password)){
                    model.addAttribute( "success", false);
                    model.addAttribute("error", false);
                    model.addAttribute("errorMessage", "");

                    //set validation token

                    return "redirect:/";
                } else {
                    model.addAttribute("error", true);
                    model.addAttribute("errorMessage", "Wrong username and/or password!");
                }
            } else {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "The given user might not have been registered yet!");
            }

        model.addAttribute( "success", false);
        return "login";
    }
}
