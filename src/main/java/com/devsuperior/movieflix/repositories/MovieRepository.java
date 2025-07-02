package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM (
            SELECT tb_movie.id,tb_movie.title, tb_movie.sub_title, tb_movie.movie_year, tb_movie.img_url
            FROM tb_movie
            WHERE (:categoryId IS NULL OR tb_movie.genre_id IN (:categoryId))
            ORDER by tb_movie.title
            ) AS tb_result
            """,
            countQuery = """
                    SELECT COUNT(*) FROM (
                    SELECT tb_movie.id,tb_movie.title, tb_movie.sub_title, tb_movie.movie_year, tb_movie.img_url
                    FROM tb_movie
                    WHERE (:categoryId IS NULL OR tb_movie.genre_id IN (:categoryId))
                    ORDER by tb_movie.title
                    ) AS tb_result
                    """)
    Page<MovieProjection> findByCategory(Long categoryId, Pageable pageable);


}
