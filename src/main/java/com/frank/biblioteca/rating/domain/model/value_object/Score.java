package com.frank.biblioteca.rating.domain.model.value_object;

public class Score {
    private final Integer value;

    public Score(Integer value) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }
        this.value = value;
    }

    public Integer getScore() { return value; }
}
