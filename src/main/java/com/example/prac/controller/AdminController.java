package com.example.prac.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Admin";
    }

}