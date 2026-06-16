package com.example.prac.model;

import org.springframework.data.annotation.Id;

import lombok.Data;
@Data
public class Question{
    @Id
    private String id;
    

    private String questiontext;

    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String createdBy;

    private int correctoption; 

    private int marks;
}


