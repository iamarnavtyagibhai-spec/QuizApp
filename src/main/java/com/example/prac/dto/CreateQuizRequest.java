package com.example.prac.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateQuizRequest {

    private String quizname;

    private String description;

    private LocalDateTime starttime;

    private LocalDateTime endtime;

    private String password;

}