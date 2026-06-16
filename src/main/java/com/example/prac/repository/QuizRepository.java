package com.example.prac.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.prac.model.Quiz;


public interface  QuizRepository extends MongoRepository<Quiz,String> {
    public List<Quiz> findByQuizname(String quiz_name);
    public  List<Quiz> findByOwneremail(String ownerEmail);
   

}
