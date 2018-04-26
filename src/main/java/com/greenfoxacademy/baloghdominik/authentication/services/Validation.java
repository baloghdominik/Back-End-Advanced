package com.greenfoxacademy.baloghdominik.authentication.services;

import com.greenfoxacademy.baloghdominik.authentication.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

/**
 * Created by baloghdominik on 2018. 04. 25..
 */

@Service
public class Validation {

    @Autowired
    UserModelRepository userModelRepository;

    @Autowired
    Encryption encryption;

    public Integer keyDate(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + now.get(Calendar.MONTH) + now.get(Calendar.DAY_OF_MONTH) + now.get(Calendar.HOUR_OF_DAY);
    }

    public String createValidationKey(String username) throws NoSuchAlgorithmException {
        String key = username + userModelRepository.findOneByUsername(username).getPassword() + keyDate().toString();
        return encryption.toMD5(key);
    }

    public String getUserIdCookie(HttpServletRequest servletRequest) {
        Cookie[] cookies = servletRequest.getCookies();
        String cookieValue = "";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userID")) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        return cookieValue;
    }

    public String getUserValidationCookie(HttpServletRequest servletRequest) {
        Cookie[] cookies = servletRequest.getCookies();
        String cookieValue = "";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userValidation")) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        return cookieValue;
    }

    public boolean isLoggedIn(HttpServletRequest response) throws NoSuchAlgorithmException {
        boolean isLogged = false;
        if (!getUserIdCookie(response).equals("") && !getUserValidationCookie(response).equals("")) {
            String username = userModelRepository.findOneById(Long.parseLong(getUserIdCookie(response))).getUsername();
            if (getUserValidationCookie(response).equals(encryption.toMD5(
                    username + userModelRepository.findOneByUsername(username).getPassword() + keyDate().toString()))){
                isLogged = true;
            }
        } else {
            isLogged = false;
        }
        return isLogged;
    }

    public String getLoggedInUsername(HttpServletRequest response) throws NoSuchAlgorithmException {
        String username = "username";
        if (isLoggedIn(response)) {
            username = userModelRepository.findOneById(Long.parseLong(getUserIdCookie(response))).getUsername();
        }
        return username;
    }


}
