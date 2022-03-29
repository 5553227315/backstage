package com.example.filmback.entity;

import lombok.Data;

@Data
public class UserEvaluate {
    private String userId;
    private String filmId;
    private String filmName;
    private String score;
    private String userEvaluate;
}
