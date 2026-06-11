package com.example.prac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.prac.Service.AuthService;
import com.example.prac.dto.LoginRequest;
import com.example.prac.dto.SignupRequest;
import com.example.prac.dto.VerifyOtpRequest;
import com.example.prac.model.Pu;
import com.example.prac.security.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    JwtService jwtservice;

    @Autowired
    AuthService authservice;

    @PostMapping("/signup")
    public Pu signup(@RequestBody SignupRequest req){
        return authservice.signupRequest(req);
    }

    @PostMapping("/verify")
    public String verifyOtp(
            @RequestBody VerifyOtpRequest req){

        return authservice.enterOtp(req);
    }

    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest req){

        return authservice.login(req);

    }
    @GetMapping("/test")
public String test(@RequestParam String token){
    return jwtservice.extractEmail(token);
}
}