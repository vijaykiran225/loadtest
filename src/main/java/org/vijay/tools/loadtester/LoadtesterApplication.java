package org.vijay.tools.loadtester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class LoadtesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoadtesterApplication.class, args);
	}

}
