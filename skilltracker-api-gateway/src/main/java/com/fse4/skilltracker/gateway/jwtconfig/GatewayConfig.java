package com.fse4.skilltracker.gateway.jwtconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				//.route("skilltracker-security", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://skilltracker-security"))
				//.route("skilltracker-query-api", r -> r.path("/skill-tracker/api/v1/admin/**").filters(f -> f.filter(filter)).uri("lb://skilltracker-query-api"))
				.build();
	}

}