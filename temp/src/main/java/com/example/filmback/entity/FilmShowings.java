package com.example.filmback.entity;

import lombok.Data;

@Data
public class FilmShowings {
    private String cinemaId;
    private String cinemaAddress;
    private String filmId;
    private String filmName;
    private String score;
    private String starTime;
    private String endTime;
    private String type;
    private String hallNumber;
    private String price;
}
