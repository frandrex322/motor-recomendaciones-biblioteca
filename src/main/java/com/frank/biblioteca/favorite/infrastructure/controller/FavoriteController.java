package com.frank.biblioteca.favorite.infrastructure.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frank.biblioteca.favorite.domain.factory.FavoriteFactory;
import com.frank.biblioteca.favorite.domain.model.Favorite;
import com.frank.biblioteca.favorite.domain.repository.FavoriteRepository;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;

    public FavoriteController(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Favorite add(@RequestBody AddFavoriteRequest request) {
        Favorite favorite = FavoriteFactory.getInstance(request.userId, request.bookId);
        favoriteRepository.save(favorite);
        return favorite;
    }

    @GetMapping("/user/{userId}")
    public List<Favorite> findByUser(@PathVariable UUID userId) {
        return favoriteRepository.findByUserId(userId);
    }

    @PatchMapping("/{id}/remove")
    public Favorite remove(@PathVariable UUID id) {
        Favorite favorite = favoriteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Favorite not found"));
        favorite.setInactive();
        favoriteRepository.save(favorite);
        return favorite;
    }

    record AddFavoriteRequest(UUID userId, UUID bookId) {}
}
