package com.example.demo.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer>{
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Account c WHERE c.accountId = :accountId")
    boolean existsByAccountId(@Param("accountId") Long accountId);
	
	public Account findByCustomerId(@Param("customerId") UUID customerId);
	
	public Account findByAccountId(@Param("accountId") Long accountId);

}