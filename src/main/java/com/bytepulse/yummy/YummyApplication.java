package com.bytepulse.yummy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
// added swagger
@EnableCaching
public class YummyApplication {
	public static void main(String[] args) {
		SpringApplication.run(YummyApplication.class, args);
	}
}
