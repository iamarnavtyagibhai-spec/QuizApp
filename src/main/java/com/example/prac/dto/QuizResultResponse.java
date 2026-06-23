package com.example.prac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultResponse {

    private int score;

    private int correctAnswers;

    private int wrongAnswers;

    private int unattempt;

    private int totalQuestions;
}