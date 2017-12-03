package com.efrei;

import com.efrei.repository.MovieRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableEurekaClient
@EnableCircuitBreaker
@EntityScan(basePackageClasses = { Application.class, Jsr310JpaConverters.class }) // todo : check if Jsr310JpaConverters can be removed
@EnableJpaRepositories(basePackageClasses = MovieRepository.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
