package com.greenfoxacademy.baloghdominik.authentication.services;

import com.greenfoxacademy.baloghdominik.authentication.models.JokeModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by baloghdominik on 2018. 04. 26..
 */

public interface JokeClient {

    @GET("/jokes/random")
    Call<JokeModel> getNewJoke(@Query("category") String category);

}
