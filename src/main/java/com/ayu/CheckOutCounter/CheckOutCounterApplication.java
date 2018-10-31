package com.ayu.CheckOutCounter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan(value= {"com.ayu.CheckOutCounter"})
public class CheckOutCounterApplication {
	@Autowired
	Environment env;
	public static void main(String[] args) {
		SpringApplication.run(CheckOutCounterApplication.class, args);
		
	}
}
