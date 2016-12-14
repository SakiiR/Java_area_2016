package com.epitech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("healthchecker")
public class AreaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AreaApplication.class, args);
	}
}
