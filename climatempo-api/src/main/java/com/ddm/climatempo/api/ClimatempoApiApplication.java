package com.ddm.climatempo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ddm.climatempo.api.config.property.ClimaTempoApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(ClimaTempoApiProperty.class)
public class ClimatempoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClimatempoApiApplication.class, args);
	}
}
