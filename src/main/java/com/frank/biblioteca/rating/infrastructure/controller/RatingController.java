package com.frank.biblioteca.rating.infrastructure.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frank.biblioteca.rating.domain.factory.RatingFactory;
import com.frank.biblioteca.rating.domain.model.Rate;
import com.frank.biblioteca.rating.domain.model.value_object.Score;
import com.frank.biblioteca.rating.domain.repository.ScoreRepository;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final ScoreRepository scoreRepository;

    public RatingController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rate create(@RequestBody CreateRatingRequest request) {
        Rate rate = RatingFactory.getInstance(request.userId, request.bookId, new Score(request.score));
        scoreRepository.save(rate);
        return rate;
    }

    @GetMapping("/book/{bookId}")
    public List<Rate> findByBook(@PathVariable UUID bookId) {
        return scoreRepository.findByBookId(bookId);
    }

    @GetMapping("/book/{bookId}/average")
    public AverageResponse averageByBook(@PathVariable UUID bookId) {
        List<Rate> ratings = scoreRepository.findByBookId(bookId);
        double avg = ratings.isEmpty() ? 0.0
            : ratings.stream().mapToInt(r -> r.getScore().getScore()).average().orElse(0.0);
        return new AverageResponse(avg, ratings.size());
    }

    record AverageResponse(double average, int total) {}
    record CreateRatingRequest(UUID userId, UUID bookId, int score) {}
}
