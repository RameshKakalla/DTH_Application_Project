package com.example.SpringBoot.Entiy;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.SpringBoot.Enums.LocationEnums.IdProofType;

@Document(collection = "registration")
public class Registration {

	@Id
	private String id;

	private String registrationId;
	private String idProofNumber;
	private IdProofType idProofType;
	private Date registrationDate;
	private String sellerId;

	private String customerProfileIdFK;
	private String setupBoxIdFK;

	// Nested objects
	private CustomerProfile customerProfile;
	private SetupBox setupBox;
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getIdProofNumber() {
		return idProofNumber;
	}

	public void setIdProofNumber(String idProofNumber) {
		this.idProofNumber = idProofNumber;
	}

	public IdProofType getIdProofType() {
		return idProofType;
	}

	public void setIdProofType(IdProofType idProofType) {
		this.idProofType = idProofType;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getCustomerProfileIdFK() {
		return customerProfileIdFK;
	}

	public void setCustomerProfileIdFK(String customerProfileIdFK) {
		this.customerProfileIdFK = customerProfileIdFK;
	}

	public String getSetupBoxIdFK() {
		return setupBoxIdFK;
	}

	public void setSetupBoxIdFK(String string) {
		this.setupBoxIdFK = string;
	}

	public CustomerProfile getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfile(CustomerProfile customerProfile) {
		this.customerProfile = customerProfile;
	}

	public SetupBox getSetupBox() {
		return setupBox;
	}

	public void setSetupBox(SetupBox setupBox) {
		this.setupBox = setupBox;
	}

	

	@Override
	public String toString() {
		return "Registration [id=" + id + ", registrationId=" + registrationId + ", idProofNumber=" + idProofNumber
				+ ", idProofType=" + idProofType + ", registrationDate=" + registrationDate + ", sellerId=" + sellerId
				+ ", customerProfileIdFK=" + customerProfileIdFK + ", setupBoxIdFK=" + setupBoxIdFK
				+ ", customerProfile=" + customerProfile + ", setupBox=" + setupBox + ", address=" + address + "]";
	}

	public Registration(String id, String registrationId, String idProofNumber, IdProofType idProofType,
			Date registrationDate, String sellerId, String customerProfileIdFK, String setupBoxIdFK,
			CustomerProfile customerProfile, SetupBox setupBox, Address address) {
		super();
		this.id = id;
		this.registrationId = registrationId;
		this.idProofNumber = idProofNumber;
		this.idProofType = idProofType;
		this.registrationDate = registrationDate;
		this.sellerId = sellerId;
		this.customerProfileIdFK = customerProfileIdFK;
		this.setupBoxIdFK = setupBoxIdFK;
		this.customerProfile = customerProfile;
		this.setupBox = setupBox;
		this.address = address;
	}

	public Registration() {
		super();
	}

}
