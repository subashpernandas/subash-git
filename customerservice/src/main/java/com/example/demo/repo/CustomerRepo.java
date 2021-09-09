package com.example.demo.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {

	Customer findByAccountId(@Param("accountId") Long accountId);
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.accountId = :accountId")
    boolean existsByAccountId(@Param("accountId") Long accountId);

	//Customer findByCustomerTransactionId(@Param("customerTransactionId") UUID customerTransactionId);
	
}
