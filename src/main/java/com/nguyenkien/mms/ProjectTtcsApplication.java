package com.nguyenkien.mms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ProjectTtcsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectTtcsApplication.class, args);
	}

}
