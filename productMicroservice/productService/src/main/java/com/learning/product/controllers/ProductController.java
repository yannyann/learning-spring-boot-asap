package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/toto")
@RestController
public class HelloController {
    @GetMapping
    public String Hello(){
        return "hello";
    }
}
