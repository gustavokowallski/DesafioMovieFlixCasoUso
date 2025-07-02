package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService service;

    @Transactional(readOnly = false)
    public ReviewDTO postReview(ReviewDTO dto) {
        Review review = new Review();
        Movie movie = movieRepository.getReferenceById(dto.getMovieId());
        User user = service.authenticated();

        review.setMovie(movie);
        review.setText(dto.getText());
        review.setUser(user);

        review = repository.save(review);
        return new ReviewDTO(review);
    }
}
