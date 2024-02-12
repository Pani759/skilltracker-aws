package com.fse4.skill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SkillApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillApplication.class, args);
	}
}
