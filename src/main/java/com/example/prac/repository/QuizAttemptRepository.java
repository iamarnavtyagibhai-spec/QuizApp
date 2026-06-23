package com.example.prac.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.prac.model.QuizAttempt;

public interface QuizAttemptRepository extends MongoRepository<QuizAttempt, String> {
    public QuizAttempt findByUserEmailAndQuizId(String email,String quizId);
    public List<QuizAttempt> findByQuizIdOrderByScoreDescAttemptedAtAsc(String quizId);



}
