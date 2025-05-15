package com.example.SpringBoot.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.SpringBoot.Entiy.CustomerProfile;

public interface CustomerProfileRepo extends MongoRepository<CustomerProfile, String> {

}
