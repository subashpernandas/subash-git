package com.example.demo.controller;


import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.repo.AccountRepo;

@RestController
@RequestMapping("/accmanagement")
public class AccountManagementController {


	@Autowired
	private AccountRepo accountRepo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountManagementController.class);

	@PostMapping("/createAccount")
	public void createAccount(@RequestBody Account accounts) { 
		LOGGER.info("enter into create account::::::::starting:" );
		Account account = new Account();
		account.setAccountHolderName(accounts.getAccountHolderName());
		account.setAccountId(accounts.getAccountId());
		account.setBranch(accounts.getBranch());
		account.setCityName(accounts.getCityName());
		account.setInitialDeposit(accounts.getInitialDeposit());
		account.setCurrentBalance(accounts.getInitialDeposit());
		account.setAccountCreatedOn(new Date());
		LOGGER.info("enter into create account::::::::ending:" );
		accountRepo.save(account);
	}

	@PostMapping("/accmgmttransaction")    
	public void getAccountTransactionDetails(@RequestBody Customer customer) {
		LOGGER.info("enter into create account::::::::starting:" );
		Account account = accountRepo.findByAccountId(customer.getAccountId());
		account.setAccountHolderName(customer.getCustomerName());
		account.setCurrentBalance(customer.getCustomerBalance());
		account.setTransactionId(customer.getTransactionId());
		account.setCustomerId(customer.getCustomerId());
		accountRepo.save(account);
		LOGGER.info("enter into create account::::::::ending:" );
		return;
	}

	@GetMapping("/accmgmttransaction/{accountId}")
	public boolean getCustomerIfExist(@PathVariable("accountId") Long accountId) {
		System.out.println("enter into acc mgmt service");
		return accountRepo.existsByAccountId(accountId);
	}

	@GetMapping("/accmgmttransactioncust/{customerId}") 
	public Account findByCustomerId(@PathVariable UUID customerId) {
		return accountRepo.findByCustomerId(customerId);
	}

	@GetMapping("/getaccmgmttransaction/{accountId}")
	public Account findByAccountId(@PathVariable Long accountId) { 
		System.out.println("enter into accountId aAccount Management");
		return accountRepo.findByAccountId(accountId); 
	}
	
}
