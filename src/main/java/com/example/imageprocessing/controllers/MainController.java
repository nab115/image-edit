package com.example.imageprocessing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "view";
    }

    @PostMapping("/")
    public String submit(@RequestBody String testInput) {

        System.out.println("Submit Working : " + testInput);
        return "view";
    }
}
