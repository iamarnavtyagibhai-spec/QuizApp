package com.example.prac.dto;

import lombok.Data;

@Data
public class AddQuestionRequest {

    private String questionText;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private int correctOption;

    private int marks;
}