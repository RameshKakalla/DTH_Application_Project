package com.example.SpringBoot.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	public ResponseEntity<?> saveRegistration(Registration request) {
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

		// If no data found
		if (registrations.isEmpty()) {
			Map<String, Object> response = new HashMap<>();
			response.put("message", "No registrations found.");
			response.put("data", Collections.emptyList());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

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

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Registrations fetched successfully");
		response.put("data", result);

		return ResponseEntity.ok(response);
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

	@Override
	public ResponseEntity<?> deleteRegistrationById(String id) {
		Map<String, Object> response = new HashMap<>();

		try {
			Registration registration = registrationRepo.findById(id).orElse(null);

			if (registration == null) {
				response.put("message", "Registration not found with id: " + id);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			// Fetch SetupBox by FK and delete
			String setupBoxId = registration.getSetupBoxIdFK();
			if (setupBoxId != null && setupBoxRepo.existsById(setupBoxId)) {
				setupBoxRepo.deleteById(setupBoxId);
				logger.info("Deleted SetupBox with ID: {}", setupBoxId);
			} else {
				logger.warn("SetupBox not found or already deleted: {}", setupBoxId);
			}

			// Fetch CustomerProfile by FK and delete
			String customerProfileId = registration.getCustomerProfileIdFK();
			if (customerProfileId != null && customerProfileRepo.existsById(customerProfileId)) {
				CustomerProfile profile = customerProfileRepo.findById(customerProfileId).orElse(null);

				// Delete Address using FK from CustomerProfile
				if (profile != null && profile.getCustomer_profile_addressIdFK() != null) {
					String addressId = profile.getCustomer_profile_addressIdFK();
					if (addressRepo.existsById(addressId)) {
						addressRepo.deleteById(addressId);
						logger.info("Deleted Address with ID: {}", addressId);
					} else {
						logger.warn("Address not found or already deleted: {}", addressId);
					}
				}

				// Delete CustomerProfile
				customerProfileRepo.deleteById(customerProfileId);
				logger.info("Deleted CustomerProfile with ID: {}", customerProfileId);
			} else {
				logger.warn("CustomerProfile not found or already deleted: {}", customerProfileId);
			}

			// Delete Registration
			registrationRepo.deleteById(id);
			logger.info("Deleted Registration with ID: {}", id);

			response.put("message", "Registration and all related data deleted successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error deleting registration", e);
			response.put("message", "Failed to delete registration");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Override
	public ResponseEntity<?> updateRegistration(Registration request) {
	    Map<String, Object> response = new HashMap<>();

	    // Get registration ID from the payload
	    String id = request.getId();
	    if (id == null || id.trim().isEmpty()) {
	        response.put("message", "ID is missing in the request.");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    try {
	        Registration existing = registrationRepo.findById(id).orElse(null);
	        if (existing == null) {
	            response.put("message", "Registration not found with id: " + id);
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }

	        // Update Address
	        if (request.getCustomerProfile() != null && request.getCustomerProfile().getAddress() != null) {
	            Address address = request.getCustomerProfile().getAddress();
	            String existingAddressId = existing.getCustomerProfile().getCustomer_profile_addressIdFK();
	            address.setAddressId(existingAddressId);
	            Address savedAddress = addressRepo.save(address);

	            // Set updated address FK to customer profile
	            request.getCustomerProfile().setCustomer_profile_addressIdFK(savedAddress.getAddressId());
	        }

	        // Update CustomerProfile
	        if (request.getCustomerProfile() != null) {
	            CustomerProfile profile = request.getCustomerProfile();
	            String existingProfileId = existing.getCustomerProfile().getCustomerProfileId();
	            profile.setCustomerProfileId(existingProfileId);
	            CustomerProfile savedProfile = customerProfileRepo.save(profile);
	            existing.setCustomerProfile(savedProfile);
	        }

	        // Update SetupBox
	        if (request.getSetupBox() != null) {
	            SetupBox setupBox = request.getSetupBox();
	            setupBox.setSetupBoxId(existing.getSetupBoxIdFK());
	            SetupBox savedBox = setupBoxRepo.save(setupBox);
	            existing.setSetupBox(savedBox);
	            existing.setSetupBoxIdFK(savedBox.getSetupBoxId());
	        }

	        // Update Registration fields
	        existing.setIdProofType(request.getIdProofType());
	        existing.setIdProofNumber(request.getIdProofNumber());
	        existing.setRegistrationDate(request.getRegistrationDate());
	        existing.setSellerId(request.getSellerId());

	        Registration saved = registrationRepo.save(existing);
	        response.put("message", "Registration updated successfully");
	        response.put("registration", saved);
	        return new ResponseEntity<>(response, HttpStatus.OK);

	    } catch (Exception e) {
	        response.put("message", "Update failed");
	        response.put("error", e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



}
