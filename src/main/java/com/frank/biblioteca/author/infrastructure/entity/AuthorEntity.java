package com.frank.biblioteca.author.infrastructure.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authors")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthorEntity {
    @Id
    private UUID id;
    private String fullName;
    private String biography;
    private LocalDate birthDate;
    private String nationality;
}
