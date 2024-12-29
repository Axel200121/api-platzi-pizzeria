package com.ams.developer.pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class AmsPizzeriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmsPizzeriaApplication.class, args);
	}

}
