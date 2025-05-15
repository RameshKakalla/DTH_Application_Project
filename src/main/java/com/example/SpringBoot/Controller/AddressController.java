package com.example.SpringBoot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBoot.ServiceImpl.AddressImpl;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
 
    @Autowired
    private AddressImpl addressImpl;

   
}
