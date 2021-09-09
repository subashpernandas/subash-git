package com.example.demo.model;

import java.util.Date;
import java.util.UUID;


public class Account {
	
	private Integer id;
		
	private Long accountId;

	private String accountHolderName;

	private String branch;

	private String cityName;

	private Integer currentBalance;
	
	private UUID customerId;
	
	private UUID transactionId;
	
	private Integer initialDeposit;
	
	private Date accountCreatedOn;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Integer currentBalance) {
		this.currentBalance = currentBalance;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getInitialDeposit() {
		return initialDeposit;
	}

	public void setInitialDeposit(Integer initialDeposit) {
		this.initialDeposit = initialDeposit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAccountCreatedOn() {
		return accountCreatedOn;
	}

	public void setAccountCreatedOn(Date accountCreatedOn) {
		this.accountCreatedOn = accountCreatedOn;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountId=" + accountId + ", accountHolderName=" + accountHolderName
				+ ", branch=" + branch + ", cityName=" + cityName + ", currentBalance=" + currentBalance
				+ ", customerId=" + customerId + ", transactionId=" + transactionId + ", initialDeposit="
				+ initialDeposit + ", getAccountId()=" + getAccountId() + ", getAccountHolderName()="
				+ getAccountHolderName() + ", getBranch()=" + getBranch() + ", getCityName()=" + getCityName()
				+ ", getCurrentBalance()=" + getCurrentBalance() + ", getCustomerId()=" + getCustomerId()
				+ ", getTransactionId()=" + getTransactionId() + ", getInitialDeposit()=" + getInitialDeposit()
				+ ", getId()=" + getId() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ "]";
	}
}
