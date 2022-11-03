package com.example.imageprocessing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "view";
    }

    @PostMapping("/")
    public String submit() {

        System.out.println("Submit Working");
        return "view";
    }
}
