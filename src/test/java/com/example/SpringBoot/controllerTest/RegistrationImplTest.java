package com.example.SpringBoot.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.SpringBoot.Entiy.Address;
import com.example.SpringBoot.Entiy.CustomerProfile;
import com.example.SpringBoot.Entiy.Registration;
import com.example.SpringBoot.Entiy.SetupBox;
import com.example.SpringBoot.Enums.LocationEnums.Country;
import com.example.SpringBoot.Enums.LocationEnums.Gender;
import com.example.SpringBoot.Enums.LocationEnums.IdProofType;
import com.example.SpringBoot.Enums.LocationEnums.SetupBoxType;
import com.example.SpringBoot.Enums.LocationEnums.State;
import com.example.SpringBoot.Enums.LocationEnums.Status;
import com.example.SpringBoot.Repository.AddressRepo;
import com.example.SpringBoot.Repository.CustomerProfileRepo;
import com.example.SpringBoot.Repository.RegistrationRepo;
import com.example.SpringBoot.Repository.SetupBoxRepo;
import com.example.SpringBoot.ServiceImpl.RegistrationImpl;

public class RegistrationImplTest {

	@InjectMocks
	private RegistrationImpl registrationService;

	@Mock
	private AddressRepo addressRepo;

	@Mock
	private CustomerProfileRepo customerProfileRepo;

	@Mock
	private SetupBoxRepo setupBoxRepo;

	@Mock
	private RegistrationRepo registrationRepo;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Positive Test Case: Verifies that all entities are saved successfully when
	 * valid Registration input is provided.
	 */
	@Test
	public void testSaveCompleteRegistration_Success() {
		Address address = new Address();
		address.setAddressId("ADDR001");

		CustomerProfile profile = new CustomerProfile();
		profile.setCustomerProfileId("CUST001");
		profile.setAddress(address);

		SetupBox setupBox = new SetupBox();
		setupBox.setSetupBoxId("BOX001");

		Registration request = new Registration();
		request.setCustomerProfile(profile);
		request.setSetupBox(setupBox);

		when(addressRepo.save(any())).thenReturn(address);
		when(customerProfileRepo.save(any())).thenReturn(profile);
		when(setupBoxRepo.save(any())).thenReturn(setupBox);
		when(registrationRepo.save(any())).thenReturn(request);

		ResponseEntity<?> response = registrationService.saveCompleteRegistration(request);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Map<?, ?> body = (Map<?, ?>) response.getBody();
		assertEquals("Data saved successfully to all collections", body.get("message"));
		assertEquals(request, body.get("registration"));
	}

	/**
	 * ❌ Negative Test Case 1: CustomerProfile is null → should throw
	 * NullPointerException and return 500.
	 */
	@Test
	public void testSaveCompleteRegistration_CustomerProfileIsNull() {
		Registration request = new Registration();
		request.setCustomerProfile(null); // Null customer profile

		ResponseEntity<?> response = registrationService.saveCompleteRegistration(request);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		Map<?, ?> body = (Map<?, ?>) response.getBody();
		assertNotNull(body.get("error"));
		assertTrue(body.get("error").toString().toLowerCase().contains("null")); // here errro is related to null or not
																					// checking

	}

	/**
	 * Negative Test Case 2: Address is null in CustomerProfile → should throw
	 * NullPointerException and return 500.
	 */
	@Test
	public void testSaveCompleteRegistration_AddressIsNull() {
		CustomerProfile profile = new CustomerProfile();
		profile.setCustomerProfileId("CUST001");
		profile.setAddress(null); // Address is null

		Registration request = new Registration();
		request.setCustomerProfile(profile);
		request.setSetupBox(new SetupBox());

		ResponseEntity<?> response = registrationService.saveCompleteRegistration(request);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		Map<?, ?> body = (Map<?, ?>) response.getBody();
		assertEquals("Failed to save data", body.get("message"));
//        assertTrue(((String) body.get("error")).contains("NullPointerException"));
		assertTrue(body.get("error").toString().toLowerCase().contains("null"));

	}

	/**
	 * Negative Test Case 3: SetupBox is null → should throw NullPointerException
	 * and return 500.
	 */
	@Test
	public void testSaveCompleteRegistration_SetupBoxIsNull() {
		Address address = new Address();
		address.setAddressId("ADDR001");

		CustomerProfile profile = new CustomerProfile();
		profile.setCustomerProfileId("CUST001");
		profile.setAddress(address);

		Registration request = new Registration();
		request.setCustomerProfile(profile);
		request.setSetupBox(null); // SetupBox is null

		when(addressRepo.save(any())).thenReturn(address);
		when(customerProfileRepo.save(any())).thenReturn(profile);

		ResponseEntity<?> response = registrationService.saveCompleteRegistration(request);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		Map<?, ?> body = (Map<?, ?>) response.getBody();
		assertEquals("Failed to save data", body.get("message"));
		assertTrue(body.get("error").toString().toLowerCase().contains("null"));
	}

	/**
	 *  Negative Test Case 4: Simulate DB failure when saving registration → should
	 * return 500 with exception message.
	 */
	@Test
	public void testSaveCompleteRegistration_DatabaseFailure() {
		Address address = new Address();
		address.setAddressId("ADDR001");

		CustomerProfile profile = new CustomerProfile();
		profile.setCustomerProfileId("CUST001");
		profile.setAddress(address);

		SetupBox setupBox = new SetupBox();
		setupBox.setSetupBoxId("BOX001");

		Registration request = new Registration();
		request.setCustomerProfile(profile);
		request.setSetupBox(setupBox);

		when(addressRepo.save(any())).thenReturn(address);
		when(customerProfileRepo.save(any())).thenReturn(profile);
		when(setupBoxRepo.save(any())).thenReturn(setupBox);

		// Simulate exception when saving registration
		when(registrationRepo.save(any())).thenThrow(new RuntimeException("DB write failed"));

		ResponseEntity<?> response = registrationService.saveCompleteRegistration(request);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		Map<?, ?> body = (Map<?, ?>) response.getBody();
		assertEquals("Failed to save data", body.get("message"));
		assertTrue(((String) body.get("error")).contains("DB write failed"));
	}

//    test cases for fetch api emthod

	// Positive Test Case: returns data with full objects
	@Test
	public void testGetAllRegistrations_WithEnums() {
		// Creating Address with Enums
		Address address = new Address("ADDR01", "123", Country.INDIA, "Main St", 5151411, State.ANDHRA_PRADESH,"Street Name");
		CustomerProfile profile = new CustomerProfile("CUST01", "John", "Doe", Gender.MALE,
				987654320, 1234567890, "john@example.com", "pic.jpg", null, address);
		SetupBox setupBox = new SetupBox("SETUP01", "192.168.0.1", SetupBoxType.HD,"v1.0", Status.ACTIVE,"1.0");

		// Creating Registration with Enums
		Registration registration = new Registration("REG01", "ID001", "Aadhar", IdProofType.AADHAAR,
				new Date(), "SELLER01", null, null, profile, setupBox, address);

		// Mocking repository call
		when(registrationRepo.findAll()).thenReturn(Collections.singletonList(registration));
		ResponseEntity<?> response = registrationService.getAllRegistrations();

		assertEquals(200, response.getStatusCodeValue());
		List<?> result = (List<?>) response.getBody();
		assertEquals(1, result.size()); // Checking that one registration is returned
	}

	// Negative Test Case: Empty registration list
	@Test
	public void testGetAllRegistrations_EmptyList() {
	    when(registrationRepo.findAll()).thenReturn(Collections.emptyList());

	    ResponseEntity<?> response = registrationService.getAllRegistrations();

	    assertEquals(404, response.getStatusCodeValue());

	    Object body = response.getBody();
	    assertNotNull(body);
	    assertTrue(body instanceof Map);

	    Map<?, ?> result = (Map<?, ?>) body;
	    assertTrue(result.containsKey("message"));
	    assertEquals("No registrations found.", result.get("message"));
	}


}
