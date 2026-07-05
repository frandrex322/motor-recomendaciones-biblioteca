package com.frank.biblioteca.review.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.frank.biblioteca.review.domain.model.value_object.ReviewStatus;

public class Review {
    private final UUID id;
    private final UUID user;
    private final UUID book;
    private String comment;
    private ReviewStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Review(UUID user, UUID book, String comment) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.book = book;
        this.comment = comment;
        this.status = ReviewStatus.PUBLISHED;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
        System.out.println("Review created with ID: " + this.id + ", User ID: " + this.user + ", Book ID: " + this.book + ", Comment: " + this.comment + ", Status: " + this.status + ", Created At: " + this.createdAt);
    }

    public void editComment(String newComment) {
        if(this.status == ReviewStatus.DELETED) {
            throw new IllegalStateException("Cannot edit comment when the review is DELETED");
        }
        this.comment = newComment;
        this.updatedAt = LocalDateTime.now();
        System.out.println("Review with ID: " + this.id + " has been updated. New Comment: " + this.comment + ", Updated At: " + this.updatedAt);
    }

    public void setPublished() {
        if(this.status == ReviewStatus.DELETED) {
            throw new IllegalStateException("Cannot set status to PUBLISHED when the review is DELETED");
        }
        this.status = ReviewStatus.PUBLISHED;
    }

    public void setDeleted() {
        if(this.status == ReviewStatus.PUBLISHED) {
            throw new IllegalStateException("Cannot set status to DELETED when the review is PUBLISHED");
        }
        this.status = ReviewStatus.DELETED;
    }

    public UUID getId() { return id; }
    public UUID getUser() { return user; }
    public UUID getBook() { return book; }
    public String getComment() { return comment; }
    public ReviewStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
