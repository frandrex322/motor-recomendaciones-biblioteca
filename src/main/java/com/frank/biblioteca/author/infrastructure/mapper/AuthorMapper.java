package com.frank.biblioteca.author.infrastructure.mapper;

import com.frank.biblioteca.author.domain.model.Author;
import com.frank.biblioteca.author.infrastructure.entity.AuthorEntity;

public class AuthorMapper {

    public static AuthorEntity toEntity(Author domain) {
        return new AuthorEntity(domain.getId(), domain.getFullName(), domain.getBiography(),
            domain.getBirthDate(), domain.getNationality());
    }

    public static Author toDomain(AuthorEntity entity) {
        return new Author(entity.getFullName(), entity.getBiography(), entity.getBirthDate(), entity.getNationality());
    }
}
