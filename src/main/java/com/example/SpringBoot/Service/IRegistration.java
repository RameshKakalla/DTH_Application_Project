package com.example.SpringBoot.Service;

import org.springframework.http.ResponseEntity;

import com.example.SpringBoot.Entiy.Registration;

public interface IRegistration {
	
	ResponseEntity<?> saveRegistration(Registration request);

	ResponseEntity<?> getAllRegistrations();
	
	ResponseEntity<?> deleteRegistrationById(String id);

	ResponseEntity<?> updateRegistration(Registration request);

}
