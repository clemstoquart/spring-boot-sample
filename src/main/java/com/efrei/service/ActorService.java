package com.efrei.service;

import java.util.List;

import com.efrei.model.Actor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActorService {

    private final CircuitBreakerFactory circuitBreakerFactory;

    public List<Actor> getMovieActors(String movieTitle) {
        RestTemplate restTemplate = new RestTemplate();

        return circuitBreakerFactory.create("getMovieActors").run(
            () -> (List<Actor>) restTemplate.getForObject("http://localhost:8081/actors/" + movieTitle, List.class),
            t -> {
                log.error("getMovieActors call failed", t);
                return defaultActors();
            }
        );
    }

    private List<Actor> defaultActors() {
        return List.of(new Actor("Doe", "John", null));
    }

}
