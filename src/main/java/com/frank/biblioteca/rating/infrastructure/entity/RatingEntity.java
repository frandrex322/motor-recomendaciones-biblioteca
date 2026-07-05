package com.frank.biblioteca.rating.infrastructure.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ratings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RatingEntity {
    @Id
    private UUID id;
    private UUID userId;
    private UUID bookId;
    private int score;
    private LocalDateTime createdAt;
}
