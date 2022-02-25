package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    List<Rating> findAll();

    Rating createRating(Rating rating);

    Optional<Rating> findById(Integer id);

    void delete(Rating rating);
}
