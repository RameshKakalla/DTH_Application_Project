package com.example.SpringBoot.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBoot.Entiy.Registration;
import com.example.SpringBoot.ServiceImpl.RegistrationImpl;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private RegistrationImpl registrationService;

	
	@PostMapping("/saveAll")
	public ResponseEntity<?> saveAll(@RequestBody Registration request) {
		Map<String, Object> response = new HashMap<>();
		try {
			logger.info("Received request to save registration: {}", request);
			ResponseEntity<?> responseEntity = registrationService.saveCompleteRegistration(request);
			logger.info("Successfully saved registration: {}", responseEntity.getBody());
			return ResponseEntity.ok(responseEntity.getBody());

		} catch (Exception e) {
			logger.error("Failed to process saveAll request", e);
			response.put("message", "Failed to save all data");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllRegistrations() {
		try {
			ResponseEntity<?> registrations = registrationService.getAllRegistrations();
			return ResponseEntity.ok(registrations.getBody());
		} catch (Exception e) {
			logger.error("Failed to fetch registrations", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Failed to retrieve registrations"));
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

		}
	}

}
