package com.example.prac.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Document(collection = "otp")
@Data
public class Otp {
    @Id
    String email;
    int otp;
    LocalDateTime expirytime;

}
