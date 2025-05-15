package com.example.SpringBoot.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.SpringBoot.Entiy.Registration;

public interface RegistrationRepo extends MongoRepository<Registration, String> {

}
