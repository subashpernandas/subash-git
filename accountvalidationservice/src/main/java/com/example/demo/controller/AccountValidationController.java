package com.example.demo.controller;

import java.util.Date;
import java.util.List;
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
import com.example.demo.model.Transaction;
import com.example.demo.openfeign.AccountManagementFeign;
import com.example.demo.openfeign.TransactionFeign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("accoutnvalidation")
public class AccountValidationController {

	private static final String url1 ="http://localhost:7777/transation/saveTransaction";
	private static final String url2 ="http://localhost:8888/accmanagement/accmgmttransaction";
	private static final String url3 ="http://localhost:8888/accmanagement/accmgmttransaction/{accountId}";
	private static final String url4 ="http://localhost:8888/accmanagement/getaccmgmttransaction/{accountId}";

	private static final String ACCOUNTVALIDATIONSERVICE="accountvalidationservice";

	
	@Autowired
	private AccountManagementFeign accountManagementFeign;

	@Autowired
	private TransactionFeign transactionFeign;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountValidationController.class);
	
	private static final String SUCCESS = "success";
	
	private static final String FAILURE = "failure";

	@GetMapping("/checkCustomerisValid/{accountId}")
	public boolean checkIfCustomerHasValidOrNot(@PathVariable("accountId") Long accountId) {
		LOGGER.info("enter into account validation");
		return accountManagementFeign.getCustomerIfExist(accountId);
	}

	@PostMapping("/accountvalidated")
	@CircuitBreaker(name = ACCOUNTVALIDATIONSERVICE, fallbackMethod = "getMsgFromFallBack")
	public String accountValidation(@RequestBody List<Customer> customer) throws Exception {
		Customer customerOne = customer.get(0);
		Customer customerTwo  = customer.get(1);
		System.out.println("enter into account account::::::::::account validated");
		Account accountForFirst = accountManagementFeign.findByAccountId(customerOne.getAccountId());
		Account accountForSecond = accountManagementFeign.findByAccountId(customerTwo.getAccountId());
		System.out.println("enter into account account::::::::::account 99999999"+ accountForFirst.getAccountHolderName());
	
		Transaction accountTransactionFrom = new Transaction(); 
		if(customerOne.getTransactionAmount() > 0 && accountForFirst.getCurrentBalance() > 0) {
			Integer transactionAftercustomerOne = accountForFirst.getCurrentBalance() - customer.get(0).getTransactionAmount(); 
			Integer transactionAftercustomerTwo = accountForSecond.getCurrentBalance() + customer.get(0).getTransactionAmount();
			accountTransactionFrom.setFromAccountId(customerOne.getAccountId());
			accountTransactionFrom.setToAccountId(customerTwo.getAccountId());
			accountTransactionFrom.setTransactionAmount(customer.get(0).getTransactionAmount());
			accountTransactionFrom.setTransactionId(UUID.randomUUID());
			accountTransactionFrom.setTransactionStatus(com.example.demo.constants.Status.PENDING.status());
			accountTransactionFrom.setTransactionType(customerOne.getTransactionType());
			accountTransactionFrom.setCustomerId(customerOne.getCustomerId());
			accountTransactionFrom.setTransactionDate(new Date());
			System.out.println("enter into account account:::::::::create transaction:::::::111111:::::");
			transactionFeign.createTransaction(accountTransactionFrom);
			System.out.println("enter into account account:::::::::create transaction::::::22222::::::"+ accountTransactionFrom);
			customer.get(0).setCustomerBalance(transactionAftercustomerOne);
			customer.get(1).setCustomerBalance(transactionAftercustomerTwo);
			customer.get(0).setTransactionId(accountTransactionFrom.getTransactionId());
			System.out.println("enter into account account:::::::::create transaction:::::::333333333:::::");
			accountManagementFeign.getAccountTransactionDetails(customer.get(0));
			System.out.println("enter into account account:::::::::create transaction:::::::444444444:::::");
			accountManagementFeign.getAccountTransactionDetails(customer.get(1));
			return AccountValidationController.SUCCESS; 
		}
		else if(customerTwo.getTransactionAmount() > 0 && accountForSecond.getCurrentBalance() > 0){
			Integer transactionAftercustomerTwo = accountForSecond.getCurrentBalance() - customer.get(1).getTransactionAmount(); 
			Integer transactionAftercustomerOne = accountForFirst.getCurrentBalance() + customer.get(1).getTransactionAmount();
			accountTransactionFrom.setFromAccountId(customerTwo.getAccountId());
			accountTransactionFrom.setToAccountId(customerOne.getAccountId());
			accountTransactionFrom.setTransactionAmount(customer.get(1).getTransactionAmount());
			accountTransactionFrom.setTransactionId(UUID.randomUUID());
			accountTransactionFrom.setTransactionStatus(com.example.demo.constants.Status.PENDING.status());
			accountTransactionFrom.setTransactionType(customerTwo.getTransactionType());
			accountTransactionFrom.setCustomerId(customerTwo.getCustomerId());
			accountTransactionFrom.setTransactionDate(new Date());
			transactionFeign.createTransaction(accountTransactionFrom);
			customer.get(1).setCustomerBalance(transactionAftercustomerTwo);
			customer.get(0).setCustomerBalance(transactionAftercustomerOne);
			customer.get(1).setTransactionId(accountTransactionFrom.getTransactionId());
			accountManagementFeign.getAccountTransactionDetails(customer.get(0));
			accountManagementFeign.getAccountTransactionDetails(customer.get(1)); 
			return AccountValidationController.SUCCESS; 
		}
		 return AccountValidationController.FAILURE; 

	}
	
	public String getMsgFromFallBack(Exception exception) {
		return "Due to Some Server Problems Or Else May Be Transaction Service Down or  ShutDown:::::::;"+
				exception.getMessage();
	}

}
