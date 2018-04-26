package com.greenfoxacademy.baloghdominik.authentication.repositories;

/**
 * Created by baloghdominik on 2018. 04. 24..
 */

import com.greenfoxacademy.baloghdominik.authentication.models.SavedJokeModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SavedJokeModelRepository extends CrudRepository<SavedJokeModel, Long> {

    public List<SavedJokeModel> findAllByUsername(String username);

    public SavedJokeModel findOneById(Long id);

}