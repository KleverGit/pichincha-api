package com.ec.pichincha;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.ec.pichincha")
@SpringBootApplication
public class PichinchaApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PichinchaApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

	}

}
