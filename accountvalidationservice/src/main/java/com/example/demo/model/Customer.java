package com.example.demo.model;

import java.util.UUID;

public class Customer {

	private String customerName;

	private Integer customerBalance;
	
	private Integer transactionAmount;

	private String transactionType;

	private UUID transactionId;
	
	private Long accountId;

	private UUID customerId;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	public Integer getCustomerBalance() {
		return customerBalance;
	}

	
	public void setCustomerBalance(Integer customerBalance) {
		this.customerBalance = customerBalance;
	}

	public String getTransactionType() {
		return transactionType;
	}

	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Integer transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
}
