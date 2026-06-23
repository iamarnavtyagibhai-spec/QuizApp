package com.example.prac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {

    private String questionId;

    private Integer selectedOption;

}
