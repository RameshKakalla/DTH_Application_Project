package com.example.SpringBoot_Crud.ServiceImpl;

import org.springframework.http.ResponseEntity;

import com.example.SpringBoot_Crud.Entiy.EmployeeEntity;

public interface EmployeeServiceImpl {
	ResponseEntity<?> saveEmployeeDetails(EmployeeEntity employeeEntity);
}
