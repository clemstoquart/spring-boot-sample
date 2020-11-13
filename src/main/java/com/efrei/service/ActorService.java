package com.efrei.service;

import java.util.List;

import com.efrei.dto.Actor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record ActorService(CircuitBreakerFactory circuitBreakerFactory) {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActorService.class);

    public List<Actor> getMovieActors(String movieTitle) {
        var restTemplate = new RestTemplate();

        return circuitBreakerFactory.create("getMovieActors").run(
            () -> (List<Actor>) restTemplate.getForObject("http://localhost:8081/actors/" + movieTitle, List.class),
            t -> {
                LOGGER.error("getMovieActors call failed", t);
                return defaultActors();
            }
        );
    }

    private static List<Actor> defaultActors() {
        return List.of(new Actor("Doe", "John", null));
    }

}
