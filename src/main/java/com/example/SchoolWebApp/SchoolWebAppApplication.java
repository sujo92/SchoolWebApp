package com.example.SchoolWebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.SchoolWebApp.repository")
@EntityScan("com.example.SchoolWebApp.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class SchoolWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolWebAppApplication.class, args);
	}

}
