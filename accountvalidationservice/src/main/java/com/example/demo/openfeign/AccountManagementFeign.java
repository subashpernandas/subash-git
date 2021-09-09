package com.example.demo.openfeign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;

@FeignClient(name="accountmanagementservice")
public interface AccountManagementFeign {

	@GetMapping("accmanagement/accmgmttransaction/{accountId}")
	public boolean getCustomerIfExist(@PathVariable Long accountId);
	
	@GetMapping("accmanagement/accmgmttransactioncust/{customerId}") 
	public Account findByCustomerId(@PathVariable UUID customerId);
	
	@GetMapping("accmanagement/getaccmgmttransaction/{accountId}")
	public Account findByAccountId(@PathVariable Long accountId);
	
	@PostMapping("accmanagement/accmgmttransaction")    
	public void getAccountTransactionDetails(@RequestBody Customer customer);
}
