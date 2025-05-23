package com.brasfi.siteinstitucional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SiteInstitucionalApplication {
	public static void main(String[] args) {
		SpringApplication.run(SiteInstitucionalApplication.class, args);
	}
}