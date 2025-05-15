package com.example.SpringBoot.Service;

import org.springframework.http.ResponseEntity;

import com.example.SpringBoot.Entiy.CustomerProfile;

public interface ICustomerProfile {
	ResponseEntity<?> saveCustomerProfile(CustomerProfile profile);

}
