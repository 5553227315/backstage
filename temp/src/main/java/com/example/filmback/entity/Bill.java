package com.example.filmback.entity;

import lombok.Data;

@Data
public class Bill {
    private String userId;
    private String billId;
    private String filmId;
    private String filmName;
    private String cinemaId;
    private String cinemaName;
    private String cinemaAddress;
    private String hallNumber;
    private String number;
    private String starTime;
    private String endTime;
    private String position;
    private String filmCover;
    private String qrCode;
    private String price;
    private String orderTime;
}
