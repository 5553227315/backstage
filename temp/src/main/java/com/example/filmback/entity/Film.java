package com.example.filmback.entity;

import lombok.Data;

@Data
public class Film {
    private String filmId;
    private String filmName;
    private String director;
    private String filmLength;
    private String filmType;
    private String filmSource;
    private String releaseDate;
    private String plotIntro;
    private String filmCover;
    private String show;
}
