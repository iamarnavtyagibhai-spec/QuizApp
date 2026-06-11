package com.example.prac.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Owner";
    }

}