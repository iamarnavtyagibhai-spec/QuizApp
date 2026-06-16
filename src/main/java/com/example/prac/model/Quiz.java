package com.example.prac.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("Quiz")
@Data
public class Quiz {
    @Id
    String id;
    String quizname;
    String description;
    Set<String> admin;

    LocalDateTime starttime;
    LocalDateTime endtime;
    String owneremail;
    String password;
    List<Question> Questions;

}
