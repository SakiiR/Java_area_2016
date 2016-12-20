package com.epitech;

import com.epitech.fixture.ModuleFixture;
import com.epitech.utils.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class 				AreaApplication {
	public static void 		main(String[] args) {
		ModuleFixture		moduleFixture = new ModuleFixture();

		moduleFixture.init();
		// load fixtures here
		SpringApplication.run(AreaApplication.class, args);
		// or here

	}
}
