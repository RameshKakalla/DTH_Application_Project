package com.example.SpringBoot.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringBoot.Entiy.Address;
import com.example.SpringBoot.Entiy.SetupBox;

@Repository
public interface AddressRepo extends MongoRepository<Address, String> {
	Optional<Address> findById(String id); // or Integer if you're using Integer IDs


}
