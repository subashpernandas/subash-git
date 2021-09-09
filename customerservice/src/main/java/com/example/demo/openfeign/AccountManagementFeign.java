package com.example.demo.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Account;

@FeignClient(name="accountmanagementservice")
public interface AccountManagementFeign {

	@GetMapping("accmanagement/getaccmgmttransaction/{accountId}")
	public Account findByAccountId(@PathVariable Long accountId);
}
