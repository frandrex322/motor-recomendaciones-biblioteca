package com.frank.biblioteca.author.domain.repository;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.author.domain.model.Author;
import com.frank.biblioteca.utils.RepositoryGeneric;

public interface AuthorRepository extends RepositoryGeneric<UUID, Author> {
    Author findByFullName(String fullName);
    List<Author> findByNationality(String nationality);
    
}  
