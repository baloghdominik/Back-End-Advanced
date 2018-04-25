package com.greenfoxacademy.baloghdominik.authentication.models;

/**
 * Created by baloghdominik on 2018. 04. 25..
 */

public class JsonResponse {

    private String result;

    public JsonResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}