package com.example.prac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

    private String id;

    private String questiontext;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private Integer marks;
}