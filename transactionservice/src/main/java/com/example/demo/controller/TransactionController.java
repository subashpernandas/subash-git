package com.example.demo.controller;

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

import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import com.example.demo.transaction.TransactionRepo;

@RestController
@RequestMapping("/transation")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
	
	@PostMapping("/saveTransaction") 
	public void createTransaction(@RequestBody Transaction transactions) {	  // 3-STEP STORE TRANSACTION DETAILS HERE
		LOGGER.info("enter into transaction:;");
	    Transaction transaction = new Transaction();
	    transaction.setTransactionDate(transactions.getTransactionDate());
	    transaction.setFromAccountId(transactions.getFromAccountId());
	    transaction.setToAccountId(transactions.getToAccountId());
	    transaction.setTransactionId(transactions.getTransactionId());
	    transaction.setTransactionAmount(transactions.getTransactionAmount());
	    transaction.setTransactionStatus(transactions.getTransactionStatus());
	    transaction.setTransactionType(transactions.getTransactionType());
	    transaction.setCustomerId(transactions.getCustomerId());
        transactionRepo.save(transaction);
		LOGGER.info("enter into transaction:;");
		return;
		
	}
	
	@GetMapping("/get/{transactionId}")
	public List<Transaction> getListOfTransaction(@PathVariable("transactionId") UUID transactionId){
		return transactionRepo.findByTransactionId(transactionId);
	}
	
	@GetMapping("/getcust/{customerId}")
	public List<Transaction> getListoFTransactionBasedOnCustomerId(@PathVariable("customerId") UUID customerId){
		return transactionRepo.findByCustomerId(customerId);
	}
}





