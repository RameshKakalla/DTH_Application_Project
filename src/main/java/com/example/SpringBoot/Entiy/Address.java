package com.example.SpringBoot.Entiy;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.SpringBoot.Enums.LocationEnums.Country;
import com.example.SpringBoot.Enums.LocationEnums.State;

@Document(collection = "address")
public class Address {

	@Id
	private String addressId;

	private String city;
	private Country country; // Enum
	private String house_Number;
	private long pincode;
	private State state; // Enum
	private String street;

	// ✅ Getters and Setters
	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getHouseNumber() {
		return house_Number;
	}

	public void setHouseNumber(String house_Number) {
		this.house_Number = house_Number;
	}

	public long getPincode() {
		return pincode;
	}

	public void setPincode(long pincode) {
		this.pincode = pincode;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	// ✅ Constructor with Enums
	public Address(String addressId, String city, Country country, String house_Number, long pincode, State state,
			String street) {
		this.addressId = addressId;
		this.city = city;
		this.country = country;
		this.house_Number = house_Number;
		this.pincode = pincode;
		this.state = state;
		this.street = street;
	}

	public Address() {
		// No-arg constructor
	}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", city=" + city + ", country=" + country + ", house_Number="
				+ house_Number + ", pincode=" + pincode + ", state=" + state + ", street=" + street + "]";
	}
}
