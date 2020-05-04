package com.test.automation.selenium.config;

import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.test.automation.selenium.framework.ExecutionInitiator;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.test.automation.selenium")
public class GetuiAutomationApplication {

	public static void main(String[] args) throws URISyntaxException {
		System.out.println(args[0]);
		if (args[0].equalsIgnoreCase("SERVER")) {
			
		SpringApplication.run(GetuiAutomationApplication.class, args);}
		else {
			try {
				ExecutionInitiator.run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	}
}
