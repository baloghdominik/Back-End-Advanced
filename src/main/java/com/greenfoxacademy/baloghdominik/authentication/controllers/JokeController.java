package com.greenfoxacademy.baloghdominik.authentication.controllers;

import com.greenfoxacademy.baloghdominik.authentication.models.SavedJokeModel;
import com.greenfoxacademy.baloghdominik.authentication.repositories.SavedJokeModelRepository;
import com.greenfoxacademy.baloghdominik.authentication.services.JokeService;
import com.greenfoxacademy.baloghdominik.authentication.services.TranslateString;
import com.greenfoxacademy.baloghdominik.authentication.services.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

/**
 * Created by baloghdominik on 2018. 04. 26..
 */

@Controller
public class JokeController {

    @Autowired
    private JokeService jokeService;

    @Autowired
    private Validation validation;

    @Autowired
    private TranslateString translateString;

    @Autowired
    private SavedJokeModelRepository savedJokeModelRepository;

    public String jokeToSave;

    @GetMapping(value = {"", "/"})
    public String Jokes(Model model, HttpServletRequest response) throws Exception {
        jokeService.jokeFromAPI();

        if (validation.isLoggedIn(response)) {
            jokeToSave = translateString.translateIt(jokeService.TheJoke);
            model.addAttribute("joke", jokeToSave);
            model.addAttribute("username", validation.getLoggedInUsername(response));
            model.addAttribute("savedJokes",
                    savedJokeModelRepository.findAllByUsername(
                    validation.getLoggedInUsername(response)));
            return "index";
        } else {
            model.addAttribute("joke", translateString.translateIt(jokeService.TheJoke));
            return "new";
        }
    }

    @GetMapping(value = {"/next"})
    public String nextJoke(){
        return "redirect:/";
    }

    @GetMapping(value = {"/save"})
    public String saveJoke(HttpServletRequest response) throws NoSuchAlgorithmException {
        savedJokeModelRepository.save(new SavedJokeModel(validation.getLoggedInUsername(response), jokeToSave));
        return "redirect:/";
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam(value = "id", required = true) Long deleteId,
                         HttpServletRequest response) throws NoSuchAlgorithmException {

        if (deleteId != null && validation.isLoggedIn(response) &&
                savedJokeModelRepository.findOneById(deleteId).getUsername()
                        .equals(validation.getLoggedInUsername(response))) {
            savedJokeModelRepository.deleteById(deleteId);
        }

        return  "redirect:/";
    }


    @GetMapping(value = "/instantlogout")
    public String logout(HttpServletResponse response) {
        Cookie cookieUser = new Cookie("userValidation", null);
        cookieUser.setPath("/");
        cookieUser.setHttpOnly(true);
        cookieUser.setMaxAge(0);
        response.addCookie(cookieUser);

        Cookie cookieID = new Cookie("userID", null);
        cookieID.setPath("/");
        cookieID.setHttpOnly(true);
        cookieID.setMaxAge(0);
        response.addCookie(cookieID);
        return  "redirect:/login2";
    }


}
