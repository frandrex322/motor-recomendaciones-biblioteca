package com.frank.biblioteca.favorite.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.frank.biblioteca.favorite.domain.model.value_object.FavoriteStatus;

public class Favorite {
    private final UUID id;
    private final UUID user;
    private final UUID book;
    private final LocalDateTime createdAt;
    private FavoriteStatus status;

    public Favorite(UUID id, UUID user, UUID book, LocalDateTime createdAt, FavoriteStatus status) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.createdAt = createdAt;
        this.status = status;
        System.out.println("Favorite created with ID: " + this.id + ", User ID: " + this.user + ", Book ID: " + this.book + ", Created At: " + this.createdAt);
    }

    public void setActive() {
        if(this.status == FavoriteStatus.ACTIVE) {
            throw new IllegalStateException("Favorite is already ACTIVE");
        }
        this.status = FavoriteStatus.ACTIVE;
    }

    public void setInactive() {
        if(this.status == FavoriteStatus.INACTIVE) {
            throw new IllegalStateException("Favorite is already INACTIVE");
        }
        this.status = FavoriteStatus.INACTIVE;
    }

    public UUID getId() { return id; }
    public UUID getUser() { return user; }
    public UUID getBook() { return book; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public FavoriteStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "favorite{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}
