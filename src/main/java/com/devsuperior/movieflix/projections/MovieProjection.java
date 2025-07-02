package com.devsuperior.movieflix.projections;

public interface MovieProjection {
    Long getId();
    String getTitle();
    String getSubTitle();
    Integer getMovie_Year();
    String getImgUrl();
}
