package com.vachan.jena_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.vachan.*" })
public class JenaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JenaDemoApplication.class, args);
	}

}
