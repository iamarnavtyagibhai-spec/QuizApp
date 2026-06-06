package com.example.prac.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javamail;
    public void sendOtp(String email,String otp){

    SimpleMailMessage msg=new SimpleMailMessage();
    msg.setTo(email);
    msg.setSubject("for otp verification");
    msg.setText("Your Otp is" +otp);
    javamail.send(msg);

}
}