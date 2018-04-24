package com.greenfoxacademy.baloghdominik.authentication.models;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by baloghdominik on 2018. 04. 24..
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserModel {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String username;
    private String password;

    public UserModel(String username, String password){
        this.username = username;
        this.password = password;
    }

    public UserModel(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
