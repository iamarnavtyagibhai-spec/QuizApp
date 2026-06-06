package com.example.prac.dto;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    @Id
    String email;
    int otp;

}
