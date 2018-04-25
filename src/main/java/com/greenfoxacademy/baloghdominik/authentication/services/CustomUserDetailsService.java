package com.greenfoxacademy.baloghdominik.authentication.services;

/**
 * Created by baloghdominik on 2018. 04. 25..
 */

import com.greenfoxacademy.baloghdominik.authentication.models.UserModel;
import com.greenfoxacademy.baloghdominik.authentication.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserModelRepository userModelRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = loadApplicationUserByUsername(username);
        return new User(userModel.getUsername(), userModel.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

    public UserModel loadApplicationUserByUsername(String username) {
        if (username != null) {
            UserModel userModel = userModelRepository.findOneByUsername(username);
            return new UserModel(userModel.getUsername(), userModel.getPassword());
        } else {
            return null;
        }
    }
}