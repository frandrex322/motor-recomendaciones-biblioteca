package com.frank.biblioteca.author.domain.factory;

import java.time.LocalDate;

import com.frank.biblioteca.author.domain.model.Author;

public class AuthorFactory {
    
    public static Author getInstance(String fullName,String biography,
        LocalDate birthDate, String nationality) {
        return new Author(fullName, biography, birthDate, nationality);
    }

}
