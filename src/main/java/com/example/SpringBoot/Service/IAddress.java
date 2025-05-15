package com.example.SpringBoot.Service;

import org.springframework.http.ResponseEntity;

import com.example.SpringBoot.Entiy.Address;

public interface IAddress {

	ResponseEntity<?> saveAddress(Address address);

}
