package com.example.filmback.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnInfo {
    private Integer retCode;
    private String message;
    private String score;
    private String adminName;
}
