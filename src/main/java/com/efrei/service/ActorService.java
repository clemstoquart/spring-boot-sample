package com.efrei.service;

import java.util.List;

import com.efrei.dto.Actor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ActorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActorService.class);

    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RestClient restClient;

    public ActorService(CircuitBreakerFactory circuitBreakerFactory, RestClient.Builder restClientBuilder) {
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.restClient = restClientBuilder.baseUrl("http://localhost:8081").build();
    }

    public List<Actor> getMovieActors(String movieTitle) {
        return circuitBreakerFactory.create("getMovieActors").run(
            () -> restClient.get()
                .uri("/actors/" + movieTitle)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {}),
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
