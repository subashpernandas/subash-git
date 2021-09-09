package com.example.demo.openfeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;

@FeignClient(name="accountvalidationservice")
public interface CustomerOpenFeign {
	
	@GetMapping(path="accoutnvalidation/checkCustomerisValid/{accountId}")
	public boolean checkIfCustomerHasValidOrNot(@PathVariable("accountId") Long accountId);
	
	@PostMapping("accoutnvalidation/accountvalidated")
	public String accountValidation(@RequestBody List<Customer> customer) throws Exception;
	
	

}
