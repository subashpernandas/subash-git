package com.example.demo.constants;

public enum Status {
	
	PENDING("pending"),
	SUCCESS("pending");
	
	private String status;
	
	private Status(String status) {
		this.status = status;
	}

	public String status() {
		return status;
	}

}
