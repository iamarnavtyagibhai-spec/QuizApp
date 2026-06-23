package com.example.prac.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "quiz_attempts")
@Data
public class QuizAttempt {

    @Id
    private String id;

    private String quizId;

    private String userEmail;

    private Integer score;

    private Integer totalQuestions;

    private Integer correctAnswers;

    private Integer wrongAnswers;
    private Integer unattempt;

    private LocalDateTime attemptedAt;
}