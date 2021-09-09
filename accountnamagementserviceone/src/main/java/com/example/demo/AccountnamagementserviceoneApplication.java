package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2 
@RefreshScope
class AccountnamagementserviceoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountnamagementserviceoneApplication.class, args);
	}

}
