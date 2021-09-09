package com.example.demo.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.Transaction;

@FeignClient(name="transactionservice")
public interface TransactionFeign {
	
	@PostMapping("transation/saveTransaction") 
	public void createTransaction(@RequestBody Transaction transactions);

}
