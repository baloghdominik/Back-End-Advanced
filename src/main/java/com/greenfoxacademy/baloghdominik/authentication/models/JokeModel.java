package com.greenfoxacademy.baloghdominik.authentication.models;

import java.util.ArrayList;

/**
 * Created by baloghdominik on 2018. 04. 26..
 */

public class JokeModel {
    private ArrayList<String> category;
    private String icon_url;
    private String id;
    private String url;
    private String value;

    public JokeModel(ArrayList<String> category, String icon_url, String id, String url, String value){
        this.category = category;
        this.icon_url = icon_url;
        this.id = id;
        this.url = url;
        this.value = value;
    }

    public JokeModel(){

    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }
}
