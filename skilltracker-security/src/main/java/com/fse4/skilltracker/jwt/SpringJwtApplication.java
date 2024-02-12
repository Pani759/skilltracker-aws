package com.fse4.skilltracker.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableEurekaClient
public class SpringJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJwtApplication.class, args);
	}

	/**
	 * Access to XMLHttpRequest at 'http://localhost:8080/skill-tracker-search/api/v1/admin/getAssociateByID/12345' 
	 * from origin 'http://localhost:8080' has been blocked by CORS policy: 
	 * No 'Access-Control-Allow-Origin' header is present on the requested resource
	 * @return
	 */
	public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }	
}
