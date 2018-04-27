package com.greenfoxacademy.baloghdominik.authentication.repositories;

import com.greenfoxacademy.baloghdominik.authentication.models.LanguageModel;
import org.springframework.data.repository.CrudRepository;

import java.security.PublicKey;

/**
 * Created by baloghdominik on 2018. 04. 27..
 */

public interface LanguageModelRepository extends CrudRepository<LanguageModel, Long> {

    public LanguageModel findOneByUsername(String username);

}
