package com.greenfoxacademy.baloghdominik.authentication.repositories;

import com.greenfoxacademy.baloghdominik.authentication.models.LanguageModel;
import com.greenfoxacademy.baloghdominik.authentication.models.TopicModel;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by baloghdominik on 2018. 04. 27..
 */

public interface TopicModelRepository extends CrudRepository<TopicModel, Long> {

    public TopicModel findOneByUsername(String username);

}
