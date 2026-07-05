package com.frank.biblioteca.utils;

import java.util.List;
import java.util.Optional;


public interface RepositoryGeneric<ID, T> {
    void save(T entity);
    Optional<T> findById(ID id);
    void deleteById(ID id);
    List<T> findAll();
    
}
