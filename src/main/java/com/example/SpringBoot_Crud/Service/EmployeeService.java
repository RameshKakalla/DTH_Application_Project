package com.example.SpringBoot_Crud.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.SpringBoot_Crud.Entiy.EmployeeEntity;
import com.example.SpringBoot_Crud.Repository.EmployeeRepository;
import com.example.SpringBoot_Crud.ServiceImpl.EmployeeServiceImpl;

@Service
public class EmployeeService implements EmployeeServiceImpl {

	@Autowired
	EmployeeRepository repository;
	
	@Override
	public ResponseEntity<?> saveEmployeeDetails(EmployeeEntity employeeEntity) {
		Map<String, Object> response=new HashMap<String, Object>();
		try {
			repository.insert(employeeEntity);
			response.put("messege", "data sucessfully saved");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("messege", "unable to save data");
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public List<EmployeeEntity> getAllEmployeeDetails() {
	    return repository.findAll();
	}

}
