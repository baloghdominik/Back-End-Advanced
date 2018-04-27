package com.greenfoxacademy.baloghdominik.authentication.services;

import com.greenfoxacademy.baloghdominik.authentication.models.JokeModel;
import com.greenfoxacademy.baloghdominik.authentication.models.TopicModel;
import com.greenfoxacademy.baloghdominik.authentication.repositories.TopicModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by baloghdominik on 2018. 04. 26..
 */

@Service
public class JokeService {
    @Autowired
    private TopicModelRepository topicModelRepository;

    @Autowired
    private Validation validation;

    public String TheJoke = "";

    public void jokeFromAPI(HttpServletRequest response) throws NoSuchAlgorithmException {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        JokeClient client = retrofit.create(JokeClient.class);

        if (topicModelRepository.findOneByUsername(validation.getLoggedInUsername(response)) != null) {
            TopicModel topic = topicModelRepository.findOneByUsername(validation.getLoggedInUsername(response));
            Call<JokeModel> call = client.getNewJoke(topic.getTopic());

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
        } else {
            Call<JokeModel> call = client.getNewJoke("dev");

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
}
