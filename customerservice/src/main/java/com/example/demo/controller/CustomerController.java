package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.openfeign.AccountManagementFeign;
import com.example.demo.openfeign.CustomerOpenFeign;
import com.example.demo.repo.CustomerRepo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/customer")
@DynamicUpdate
public class CustomerController {

	private static final String CUSTOMERSERVICE="customerservice";

	private static final String SUCCESS = "success";

	private static final String FAILURE = "failure";

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CustomerOpenFeign customerOpenFeign;

	@Autowired
	private AccountManagementFeign accountManagementFeign;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	@PostMapping("/post")
	@CircuitBreaker(name = CUSTOMERSERVICE, fallbackMethod = "getMsgFromFallBack")
	public String getInformation(@RequestBody List<Customer> customers) {  // 2-STEP CUSOMER IS VALID OR NOT
		LOGGER.info("enter into customer before starting");
		String  response = null;
		boolean checkFirstAccountHolder = customerOpenFeign.checkIfCustomerHasValidOrNot(customers.get(0).getAccountId());
		LOGGER.info("enter into customer 2222222"+ checkFirstAccountHolder);
		boolean checkSecondAccountHolder = customerOpenFeign.checkIfCustomerHasValidOrNot(customers.get(1).getAccountId());
		LOGGER.info("enter into customer 2222222 4444444"+ checkSecondAccountHolder);
		if(Boolean.TRUE.equals(checkFirstAccountHolder) && Boolean.TRUE.equals(checkSecondAccountHolder)) {
			Customer customerIsPresentOrNotForFirst = customerRepo.findByAccountId(customers.get(0).getAccountId());
			Customer customerIsPresentOrNotSecond = customerRepo.findByAccountId(customers.get(1).getAccountId());
			if(customerIsPresentOrNotForFirst == null){
				customers.get(0).setCustomerId(UUID.randomUUID());               // CREATE CUSTOMERID DURING FIRST TIME THE CUSTOMER  ENTER
				customerRepo.save(customers.get(0));
			}
			else {
				customerIsPresentOrNotForFirst.setTransactionAmount(customers.get(0).getTransactionAmount());  
				customerIsPresentOrNotForFirst.setTransactionType(customers.get(0).getTransactionType());
				customerIsPresentOrNotForFirst.setCustomerName(customers.get(0).getCustomerName());
				customers.get(0).setCustomerId(customerIsPresentOrNotForFirst.getCustomerId());
				customerRepo.save(customerIsPresentOrNotForFirst);
			}
			if(customerIsPresentOrNotSecond == null){
				customers.get(1).setCustomerId(UUID.randomUUID());               // CREATE CUSTOMERID DURING FIRST TIME THE CUSTOMER  ENTER
				customerRepo.save(customers.get(1));
			}
			else {
				customerIsPresentOrNotSecond.setTransactionAmount(customers.get(1).getTransactionAmount());  
				customerIsPresentOrNotSecond.setTransactionType(customers.get(1).getTransactionType());
				customerIsPresentOrNotSecond.setCustomerName(customers.get(1).getCustomerName());
				customers.get(1).setCustomerId(customerIsPresentOrNotSecond.getCustomerId());
				customerRepo.save(customerIsPresentOrNotSecond);
			}
		}

		/*
		 * if(Boolean.FALSE.equals(checkFirstAccountHolder)){ throw new
		 * Exception("OOPS !!!!!!!!!! Doesn't a 0000000 Valid Customer"+
		 * customers.get(0).getCustomerName());
		 * 
		 * } else if(Boolean.FALSE.equals(checkSecondAccountHolder)) { throw new
		 * Exception("OOPS !!!!!!!!!! Doesn't a 1111111111 Valid Customer"+
		 * customers.get(1).getCustomerName()); }
		 */

		LOGGER.info("enter into customer 2222222 4444444 ttttttt");
		try {
			response  = customerOpenFeign.accountValidation(customers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * if(Boolean.FALSE.equals(response)) { throw new
		 * Exception("OOPS !!!!!!!!!!!!!!! If Atleast 2333333333 AnyOneOf Them Should Have Make an Account Transmission"
		 * ); }
		 */
		if(response.equals(CustomerController.SUCCESS)) {
			if(customers.get(0).getTransactionAmount() > 0) {
				LOGGER.info("enter into customer 2222222 4444444 subashstore");
				Account account = accountManagementFeign.findByAccountId(customers.get(0).getAccountId());
				Account accountTwo = accountManagementFeign.findByAccountId(customers.get(1).getAccountId());
				LOGGER.info("enter into customer 2222222 4444444 333333333333");
				Customer customer = customerRepo.findByAccountId(customers.get(0).getAccountId());
				Customer customerTwo = customerRepo.findByAccountId(customers.get(1).getAccountId());
				LOGGER.info("enter into customer 2222222 4444444 subashstore1111111111111");
				customer.setCustomerBalance(account.getCurrentBalance());
				customer.setTransactionId(account.getTransactionId());
				customerTwo.setCustomerBalance(accountTwo.getCurrentBalance());
				customerRepo.save(customer);
				customerRepo.save(customerTwo);
				LOGGER.info("enter into customer 2222222 4444444 subashstore1111111111111");
				return customers.get(0).toString();
			} 
			else if(customers.get(1).getTransactionAmount() > 0) {
				Account account = accountManagementFeign.findByAccountId(customers.get(0).getAccountId());
				Account accountTwo = accountManagementFeign.findByAccountId(customers.get(1).getAccountId());
				Customer customer = customerRepo.findByAccountId(customers.get(1).getAccountId());
				Customer customerTwo = customerRepo.findByAccountId(customers.get(0).getAccountId());
				customerTwo.setCustomerBalance(account.getCurrentBalance());
				customerTwo.setTransactionId(account.getTransactionId());
				customer.setCustomerBalance(accountTwo.getCurrentBalance());
				customerRepo.save(customerTwo);
				customerRepo.save(customer);
				return customers.get(1).toString();
			}
		} 
		return response;
	}


	public String getMsgFromFallBack(Exception exception) {
		return "Due to Some Server Problems Or Else May Be Account Validation Server ShutDown:::::::;"+
				exception.getMessage();
	}
}





