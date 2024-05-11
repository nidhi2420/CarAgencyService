package com.carserviceagency;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableSwagger2
public class CarServiceAgencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceAgencyApplication.class, args);
	
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
