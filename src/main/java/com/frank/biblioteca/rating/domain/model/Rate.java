package com.frank.biblioteca.rating.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.frank.biblioteca.rating.domain.model.value_object.Score;

public class Rate {
    private final UUID id;
    private final UUID userId;
    private final UUID bookId;
    private final Score score;
    private final LocalDateTime createdAt;

    public Rate(UUID userId, UUID bookId, Score score) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.bookId = bookId;
        this.score = score;
        this.createdAt = LocalDateTime.now();
        System.out.println("Rate created with ID: " + this.id + ", User ID: " + this.userId + ", Book ID: " + this.bookId + ", Score: " + this.score.getScore() + ", Created At: " + this.createdAt);
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getBookId() { return bookId; }
    public Score getScore() { return score; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", score=" + score.getScore() +
                ", createdAt=" + createdAt +
                '}';
    }
}
