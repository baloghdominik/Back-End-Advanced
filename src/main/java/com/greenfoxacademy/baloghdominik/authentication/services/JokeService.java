package com.greenfoxacademy.baloghdominik.authentication.services;

import com.greenfoxacademy.baloghdominik.authentication.models.JokeModel;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by baloghdominik on 2018. 04. 26..
 */

@Service
public class JokeService {
    public String TheJoke = "";

    public void jokeFromAPI() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        JokeClient client = retrofit.create(JokeClient.class);
        Call<JokeModel> call = client.getNewJoke();

        call.enqueue(new Callback<JokeModel>() {
            @Override
            public void onResponse(Call<JokeModel> call, Response<JokeModel> response) {
                JokeModel jokeModel = response.body();
                TheJoke = jokeModel.getValue();
            }

            @Override
            public void onFailure(Call<JokeModel> call, Throwable t) {
                System.out.println("Something went wrong!");
            }
        });
    }
}
