package com.devsuperior.movieflix.controllers;



import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.GenreService;
import com.devsuperior.movieflix.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;

    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDetailsDTO> findByID(@PathVariable Long id){
        MovieDetailsDTO movieReceive = movieService.findById(id);
        return ResponseEntity.ok(movieReceive);
    }

    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
    @GetMapping
    public ResponseEntity<Page<MovieCardDTO>> findByCategoryId(@RequestParam(defaultValue = "")Long genreId, Pageable pageable){
        Page<MovieCardDTO> dtos = movieService.findMoviesByCategoryId(genreId, pageable);
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
    @GetMapping(value = "/{filmId}/reviews")
    public ResponseEntity<List<ReviewDTO>> findReviewsByFilmId(@PathVariable Long filmId){
        List<ReviewDTO> movieReceive = movieService.findReviewsByMovieId(filmId);
        return ResponseEntity.ok(movieReceive);
    }
}
