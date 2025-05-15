package com.example.SpringBoot.Entiy;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.SpringBoot.Enums.LocationEnums.Gender;

@Document(collection = "customer_profile")
public class CustomerProfile {

	@Id
	private String customerProfileId;

	private String firstName;
	private String lastName;
	private Gender gender;           //Enum
	private long phoneNumber;
	private long alterPhoneNumber;
	private String email;
	private String profilePic;

	private String customer_profile_addressIdFK;

//	@Transient
	private Address address;

	public String getCustomerProfileId() {
		return customerProfileId;
	}

	public void setCustomerProfileId(String customerProfileId) {
		this.customerProfileId = customerProfileId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public long getAlterPhoneNumber() {
		return alterPhoneNumber;
	}

	public void setAlterPhoneNumber(long alterPhoneNumber) {
		this.alterPhoneNumber = alterPhoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getCustomer_profile_addressIdFK() {
		return customer_profile_addressIdFK;
	}

	public void setCustomer_profile_addressIdFK(String customer_profile_addressIdFK) {
		this.customer_profile_addressIdFK = customer_profile_addressIdFK;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "CustomerProfile [customerProfileId=" + customerProfileId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", gender=" + gender + ", phoneNumber=" + phoneNumber + ", alterPhoneNumber="
				+ alterPhoneNumber + ", email=" + email + ", profilePic=" + profilePic
				+ ", customer_profile_addressIdFK=" + customer_profile_addressIdFK + ", address=" + address + "]";
	}

	public CustomerProfile(String customerProfileId, String firstName, String lastName, Gender gender, long phoneNumber,
			long alterPhoneNumber, String email, String profilePic, String customer_profile_addressIdFK,
			Address address) {
		super();
		this.customerProfileId = customerProfileId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.alterPhoneNumber = alterPhoneNumber;
		this.email = email;
		this.profilePic = profilePic;
		this.customer_profile_addressIdFK = customer_profile_addressIdFK;
		this.address = address;
	}

	public CustomerProfile() {
		super();
	}

}
