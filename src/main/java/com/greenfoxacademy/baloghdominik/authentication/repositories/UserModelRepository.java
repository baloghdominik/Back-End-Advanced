package com.greenfoxacademy.baloghdominik.authentication.repositories;

/**
 * Created by baloghdominik on 2018. 04. 24..
 */

import com.greenfoxacademy.baloghdominik.authentication.models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserModelRepository extends CrudRepository<UserModel, Long> {

    public UserModel findOneByUsername(String username);

    public List<UserModel> findAllByUsername(String username);

    public UserModel findOneById(Long id);
}