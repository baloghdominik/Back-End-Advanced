package com.greenfoxacademy.baloghdominik.authentication.controllers;

import com.greenfoxacademy.baloghdominik.authentication.models.LanguageModel;
import com.greenfoxacademy.baloghdominik.authentication.models.SavedJokeModel;
import com.greenfoxacademy.baloghdominik.authentication.repositories.LanguageModelRepository;
import com.greenfoxacademy.baloghdominik.authentication.repositories.SavedJokeModelRepository;
import com.greenfoxacademy.baloghdominik.authentication.services.JokeService;
import com.greenfoxacademy.baloghdominik.authentication.services.TranslateString;
import com.greenfoxacademy.baloghdominik.authentication.services.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    private JokeService jokeService;

    private Validation validation;

    private TranslateString translateString;

    private SavedJokeModelRepository savedJokeModelRepository;

    private LanguageModelRepository languageModelRepository;

    private String jokeToSave;

    @Autowired
    public JokeController(JokeService jokeService,
                          Validation validation,
                          TranslateString translateString,
                          SavedJokeModelRepository savedJokeModelRepository,
                          LanguageModelRepository languageModelRepository) {
        this.jokeService = jokeService;
        this.validation = validation;
        this.translateString = translateString;
        this.savedJokeModelRepository = savedJokeModelRepository;
        this.languageModelRepository = languageModelRepository;
    }

    @GetMapping(value = {"", "/"})
    public String Jokes(Model model, HttpServletRequest response) throws Exception {
        jokeService.jokeFromAPI();

        if (validation.isLoggedIn(response)) {
            jokeToSave = selectLanguage(response);
            model.addAttribute("joke", selectLanguage(response));
            model.addAttribute("username", validation.getLoggedInUsername(response));
            model.addAttribute("savedJokes",
                    savedJokeModelRepository.findAllByUsername(
                    validation.getLoggedInUsername(response)));
            return "index";
        } else {
            model.addAttribute("joke", jokeService.TheJoke);
            return "new";
        }
    }

    private String selectLanguage(HttpServletRequest response) throws Exception {
        if (languageModelRepository.findOneByUsername(validation.getLoggedInUsername(response)) != null){
            LanguageModel langModel = languageModelRepository.findOneByUsername(validation.getLoggedInUsername(response));
            if (!langModel.getLanguage().equals("en")){
                return translateString.translateIt(jokeService.TheJoke, langModel.getLanguage());
            }
            return jokeService.TheJoke;
        } else {
            return jokeService.TheJoke;
        }
    }

    @GetMapping(value = {"/load"})
    public String loadJoke(HttpServletRequest response) throws Exception {
        jokeService.jokeFromAPI();
        jokeToSave = selectLanguage(response);
        return "redirect:/";
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

    @GetMapping(value = "/settings")
    public String settings(Model model, HttpServletRequest response) throws Exception {
        if (validation.isLoggedIn(response)) {
            model.addAttribute("username", validation.getLoggedInUsername(response));
            if (languageModelRepository.findOneByUsername(validation.getLoggedInUsername(response)) == null){
                model.addAttribute("currentLang", "EN");
            } else {
                model.addAttribute("currentLang", languageModelRepository.findOneByUsername(
                        validation.getLoggedInUsername(response)).getLanguage().toUpperCase());
            }
            return "settings";
        } else {
            return "redirect:/login2";
        }
    }

    @PostMapping(value = "/set")
    public String setLang(@ModelAttribute(name = "lang") String lang, Model model, HttpServletRequest response) throws Exception {
        if (validation.isLoggedIn(response)) {
            if (languageModelRepository.findOneByUsername(validation.getLoggedInUsername(response)) != null){
                LanguageModel currentLanguageModel = languageModelRepository.findOneByUsername(validation.getLoggedInUsername(response));
                currentLanguageModel.setLanguage(lang);
                languageModelRepository.save(currentLanguageModel);
            } else {
                languageModelRepository.save(new LanguageModel(lang, validation.getLoggedInUsername(response)));
            }
            model.addAttribute("username", validation.getLoggedInUsername(response));
            return "redirect:/";
        } else {
            return "redirect:/login2";
        }
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
