package com.greenfoxacademy.baloghdominik.authentication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by baloghdominik on 2018. 04. 24..
 */

@Controller
public class MainController {

    @GetMapping(value = {"", "/"})
    public String indexPage(){
        return "index";
    }

}
