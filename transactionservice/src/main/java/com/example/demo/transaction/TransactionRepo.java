package com.example.demo.transaction;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction,Integer>{

	List<Transaction> findByTransactionId(@Param("transactionId") UUID transactionId);
	
	List<Transaction> findByCustomerId(@Param("customerId") UUID customerId);


}
