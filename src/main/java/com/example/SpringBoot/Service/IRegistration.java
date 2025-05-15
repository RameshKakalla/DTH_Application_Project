package com.example.SpringBoot.Service;

import org.springframework.http.ResponseEntity;

import com.example.SpringBoot.Entiy.Registration;

public interface IRegistration {

	ResponseEntity<?> saveCompleteRegistration(Registration request);

	ResponseEntity<?> getAllRegistrations();
}
