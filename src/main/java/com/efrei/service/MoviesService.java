package com.efrei.service;

import com.efrei.model.Actor;
import com.efrei.model.Movie;
import com.efrei.model.MovieType;
import com.efrei.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MoviesService {

    private final MovieRepository movieRepository;
    private final ActorService actorService;

    @GetMapping
    public ResponseEntity findAll() {
        List<Movie> movies = movieRepository.findAll();

        if (movies.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            movies.forEach(this::populateMovieWithActors);

            return ResponseEntity.ok(movies);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        Optional<Movie> movie = movieRepository.findById(id);

        return movie.<ResponseEntity>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity createMovie(@RequestBody Movie movie) {
        movieRepository.save(movie);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity updateMovie(@RequestBody Movie movie) {
        movieRepository.save(movie);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMovie(@PathVariable Long id) {
        movieRepository.delete(movieRepository.getOne(id));

        return ResponseEntity.noContent().build();
    }

    private void populateMovieWithActors(Movie movie) {
        List<Actor> movieActors = actorService.getMovieActors(movie.getTitle());

        movieActors.forEach(movie::addActor);
    }

    @PostConstruct
    public void initDatabase() {
        Movie interstellar = Movie.builder()
                .director("Christopher Nolan")
                .releaseDate(LocalDate.of(2014, Month.NOVEMBER, 7))
                .type(MovieType.SCIFI)
                .title("Interstellar")
                .build();

        Movie snowden = Movie.builder()
                .director("Oliver Stone")
                .releaseDate(LocalDate.of(2016, Month.SEPTEMBER, 16))
                .type(MovieType.DRAMA)
                .title("Snowden")
                .build();

        movieRepository.saveAll(Arrays.asList(interstellar, snowden));
    }
}
