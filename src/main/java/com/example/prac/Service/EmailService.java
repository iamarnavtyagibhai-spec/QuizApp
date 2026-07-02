package com.example.prac.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendOtp(String email, String otp) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("iamarnavtyagig@gmail.com");
            message.setTo(email);
            message.setSubject("Quiz App OTP Verification");
            message.setText(
                    "Hello,\n\n" +
                    "Your OTP is: " + otp + "\n\n" +
                    "This OTP is valid for 5 minutes.\n\n" +
                    "Thank You!"
            );

            System.out.println("Sending OTP to: " + email);

            javaMailSender.send(message);

            System.out.println("OTP sent successfully.");

            return "OTP Sent Successfully";

        } catch (Exception e) {

            e.printStackTrace();
            return "Failed to send OTP";
        }
    }
}