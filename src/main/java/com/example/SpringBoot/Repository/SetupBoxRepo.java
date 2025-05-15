package com.example.SpringBoot.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.SpringBoot.Entiy.SetupBox;

public interface SetupBoxRepo extends MongoRepository<SetupBox, String> {

}
