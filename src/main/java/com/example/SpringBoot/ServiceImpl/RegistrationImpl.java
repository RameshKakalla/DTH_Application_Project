package com.example.SpringBoot.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.SpringBoot.Entiy.Address;
import com.example.SpringBoot.Entiy.CustomerProfile;
import com.example.SpringBoot.Entiy.Registration;
import com.example.SpringBoot.Entiy.SetupBox;
import com.example.SpringBoot.Repository.AddressRepo;
import com.example.SpringBoot.Repository.CustomerProfileRepo;
import com.example.SpringBoot.Repository.RegistrationRepo;
import com.example.SpringBoot.Repository.SetupBoxRepo;
import com.example.SpringBoot.Service.IRegistration;

@Service
public class RegistrationImpl implements IRegistration {
	private static final Logger logger = LoggerFactory.getLogger(RegistrationImpl.class);

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private CustomerProfileRepo customerProfileRepo;

	@Autowired
	private SetupBoxRepo setupBoxRepo;

	@Autowired
	private RegistrationRepo registrationRepo;

	@Override
	public ResponseEntity<?> saveCompleteRegistration(Registration request) {
		Map<String, Object> response = new HashMap<>();
		try {
			// Save Address
			Address savedAddress = addressRepo.save(request.getCustomerProfile().getAddress());
			logger.info("Address saved successfully: {}", savedAddress);

			// Save CustomerProfile
			CustomerProfile profile = request.getCustomerProfile();
			profile.setCustomer_profile_addressIdFK(savedAddress.getAddressId());
			CustomerProfile savedProfile = customerProfileRepo.save(profile);
			logger.info("CustomerProfile saved successfully: {}", savedProfile);

			// Save SetupBox
			SetupBox savedBox = setupBoxRepo.save(request.getSetupBox()); 
			logger.info("SetupBox saved successfully: {}", savedBox);

			// Prepare Registration with FK refs
			request.setCustomerProfileIdFK(savedProfile.getCustomerProfileId());
			request.setSetupBoxIdFK(savedBox.getSetupBoxId());

			Registration savedRegistration = registrationRepo.save(request);
			logger.info("Registration saved successfully: {}", savedRegistration);

			response.put("message", "Data saved successfully to all collections");
			response.put("registration", savedRegistration);
			return new ResponseEntity<>(response, HttpStatus.CREATED);

		} catch (Exception e) {
			logger.error("Failed to save complete registration", e);
			response.put("message", "Failed to save data");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getAllRegistrations() {
		List<Registration> registrations = registrationRepo.findAll();
		List<Map<String, Object>> result = new ArrayList<>();

		for (Registration reg : registrations) {
			try {
			Map<String, Object> registrationMap = new HashMap<>();

			registrationMap.put("registrationId", reg.getRegistrationId());
			registrationMap.put("id", reg.getId());
			registrationMap.put("idProofType", reg.getIdProofType());
			registrationMap.put("idProofNumber", reg.getIdProofNumber());
			registrationMap.put("registrationDate", reg.getRegistrationDate());
			registrationMap.put("sellerId", reg.getSellerId());

			registrationMap.put("setupBox", buildSetupBoxMap(reg.getSetupBox()));
			registrationMap.put("customerProfile", buildCustomerProfileMap(reg.getCustomerProfile()));

			result.add(registrationMap);
		} catch (Exception ex) {
			logger.warn("Failed to process registration with ID: " + reg.getId(), ex);
		}
		}
		return ResponseEntity.ok(result);
	}

	private Map<String, Object> buildSetupBoxMap(SetupBox setupBox) {
		if (setupBox == null)
			return null;

		Map<String, Object> map = new HashMap<>();
		map.put("setupBoxId", setupBox.getSetupBoxId());
		map.put("ipAddress", setupBox.getIpAddress());
		map.put("setBoxType", setupBox.getSetBoxType());
		map.put("software", setupBox.getSoftware());
		map.put("status", setupBox.getStatus());
		map.put("version", setupBox.getVersion());
		return map;
	}

	private Map<String, Object> buildCustomerProfileMap(CustomerProfile profile) {
		if (profile == null)
			return null;

		Map<String, Object> map = new HashMap<>();
		map.put("customerProfileId", profile.getCustomerProfileId());
		map.put("firstName", profile.getFirstName());
		map.put("lastName", profile.getLastName());
		map.put("gender", profile.getGender());
		map.put("phoneNumber", profile.getPhoneNumber());
		map.put("alterPhoneNumber", profile.getAlterPhoneNumber());
		map.put("email", profile.getEmail());
		map.put("profilePic", profile.getProfilePic());

		map.put("address", buildAddressMap(profile.getAddress()));
		return map;
	}

	private Map<String, Object> buildAddressMap(Address address) {
		if (address == null)
			return null;

		Map<String, Object> map = new HashMap<>();
		map.put("addressId", address.getAddressId());
		map.put("houseNumber", address.getHouseNumber());
		map.put("street", address.getStreet());
		map.put("city", address.getCity());
		map.put("state", address.getState());
		map.put("country", address.getCountry());
		map.put("pincode", address.getPincode());
		return map;
	}

}
