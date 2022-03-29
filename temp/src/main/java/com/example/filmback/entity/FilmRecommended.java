package com.example.filmback.entity;

import lombok.Data;

@Data
public class FilmRecommended {
    private String filmId;
    private String filmName;
    private String director;
    private String filmLength;
    private String filmType;
    private String cinemaType;
    private String filmCover;
    private String score;
}
