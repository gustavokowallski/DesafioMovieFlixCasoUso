package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id){
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filme inexistente"));
        return new MovieDetailsDTO(movie);
    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findMoviesByCategoryId(Long categoryId, Pageable pageable){
       Page<MovieProjection> pageProjections = repository.findByCategory(categoryId, pageable);
        List<MovieCardDTO> list = new ArrayList<>();

        for (MovieProjection projec: pageProjections){
            MovieCardDTO entity = new MovieCardDTO();
            entity.setId(projec.getId());
            entity.setTitle(projec.getTitle());
            entity.setSubTitle(projec.getSubTitle());
            entity.setYear(projec.getMovie_Year());
            entity.setImgUrl(projec.getImgUrl());
            list.add(entity);
        }
        return new PageImpl<>(list, pageProjections.getPageable(), pageProjections.getTotalElements());

    }

    @Transactional
    public List<ReviewDTO> findReviewsByMovieId(Long filmId) {
        Movie movie = repository.getReferenceById(filmId);
        List<ReviewDTO> reviewsDtoList = new ArrayList<>();

        for(Review review: movie.getReviews()){
            ReviewDTO dto = new ReviewDTO(review);
            reviewsDtoList.add(dto);
        }

        return reviewsDtoList;
    }
}
