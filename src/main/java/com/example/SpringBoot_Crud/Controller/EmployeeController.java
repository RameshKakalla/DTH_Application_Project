package com.example.SpringBoot_Crud.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBoot_Crud.Entiy.EmployeeEntity;
import com.example.SpringBoot_Crud.Service.EmployeeService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ApiEmployee")
@Tag(name = "Employee API", description = "Operations related to Employee entity")

public class EmployeeController {

	Logger logger=LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;
	
	
	
	@PostMapping("/SaveEmployeeData")
	public ResponseEntity<?> saveData(@RequestBody EmployeeEntity employeeEntity){
		Map<String, Object> response=new HashMap<>();
		
		try {
			ResponseEntity<?> res=employeeService.saveEmployeeDetails(employeeEntity);
			if(res.getStatusCode().equals(HttpStatus.OK)) {
				response.put("messege", "successfully saved data");
				response.put("data", employeeEntity);
				response.put("status", HttpStatus.OK);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}else {
				response.put("messege", "unable to save dat");
				response.put("data", employeeEntity);
				response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("status", HttpStatus.INTERNAL_SERVER_ERROR);

		}

		
	}
	
	
	@GetMapping("/GetAllEmployees")
	public ResponseEntity<?> getAllEmployees() {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        List<EmployeeEntity> employees = employeeService.getAllEmployeeDetails();
	        
	        if (employees.isEmpty()) {
	            response.put("message", "No employees found.");
	            response.put("status", HttpStatus.NOT_FOUND);
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        } else {
	            response.put("message", "Employees retrieved successfully.");
	            response.put("data", employees);
	            response.put("status", HttpStatus.OK);
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        }
	    } catch (Exception e) {
	        response.put("message", "An error occurred while fetching employees.");
	        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
