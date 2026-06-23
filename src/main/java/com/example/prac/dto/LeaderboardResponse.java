package com.example.prac.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LeaderboardResponse {

    private int rank;

    private String userEmail;

    private int score;

    private LocalDateTime attemptedAt;

}