package com.example.SpringBoot.Enums;

public class LocationEnums {
	public enum State {
		ANDHRA_PRADESH, KARNATAKA, MAHARASHTRA, TAMIL_NADU, KERALA,
		// Add more as needed
	}

	public enum Country {
		INDIA,
		// Add more as needed USA, UK, AUSTRALIA, CANADA
		
	}

	public enum Gender {
		MALE, FEMALE, OTHER
	}

	public enum IdProofType {
		AADHAAR, PASSPORT, PAN, VOTER_ID, DRIVER_LICENSE
	}

	public enum SetupBoxType {
		HD, SD, DVR
	}

	public enum Status {
		ACTIVE, INACTIVE, BLOCKED
	}
}
