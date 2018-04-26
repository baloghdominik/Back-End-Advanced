package com.greenfoxacademy.baloghdominik.authentication.controllers;

import com.greenfoxacademy.baloghdominik.authentication.models.UserModel;
import com.greenfoxacademy.baloghdominik.authentication.repositories.UserModelRepository;
import com.greenfoxacademy.baloghdominik.authentication.services.Encryption;
import com.greenfoxacademy.baloghdominik.authentication.services.LoginValidation;
import com.greenfoxacademy.baloghdominik.authentication.services.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

/**
 * Created by baloghdominik on 2018. 04. 24..
 */

@Controller
public class LoginController {

    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    private LoginValidation loginValidation;

    @Autowired
    private Encryption encryption;

    @Autowired
    private Validation validation;

    public LoginController(UserModelRepository userModelRepository,
                           LoginValidation loginValidation,
                           Encryption encryption){
        this.userModelRepository = userModelRepository;
        this.loginValidation = loginValidation;
        this.encryption = encryption;
    }

    @GetMapping(value = "/login2")
    public String loginPage(Model model){
        model.addAttribute( "success", false);
        model.addAttribute("error", false);
        model.addAttribute("errorMessage", "");
        return "login";
    }

    @PostMapping(value = "/login2")
    public String loginUser(@ModelAttribute(value="username") String username,
                            @ModelAttribute(value = "password") String password,
                            Model model, HttpServletResponse response) throws NoSuchAlgorithmException {

            if (loginValidation.isUserExists(username)) {
                if (loginValidation.isPasswordMatchToUsername(username, password)){
                    model.addAttribute( "success", false);
                    model.addAttribute("error", false);
                    model.addAttribute("errorMessage", "");

                        UserModel thisUser = userModelRepository.findOneByUsername(username);
                        Cookie cookie = new Cookie("userValidation", validation.createValidationKey(username));
                        cookie.setPath("/");
                        cookie.setMaxAge(100000);
                        response.addCookie(cookie);
                        Cookie cookieID = new Cookie("userID", thisUser.getId().toString());
                        cookieID.setPath("/");
                        cookieID.setMaxAge(100000);
                        response.addCookie(cookieID);

                    return "redirect:/";
                } else {
                    model.addAttribute("error", true);
                    model.addAttribute("errorMessage",
                            "Wrong username and/or password!");
                }
            } else {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage",
                        "The given user might not have been registered yet!");
            }

        model.addAttribute( "success", false);
        return "login";
    }
}
