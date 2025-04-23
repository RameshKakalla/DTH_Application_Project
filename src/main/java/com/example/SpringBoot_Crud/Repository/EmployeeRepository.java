package com.example.SpringBoot_Crud.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringBoot_Crud.Entiy.EmployeeEntity;

@Repository
public interface EmployeeRepository  extends MongoRepository<EmployeeEntity, Integer>{

}
