package com.example.imageprocessing.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.Base64;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "view";
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submit(@RequestBody String testInput) {

        System.out.println("Submit Working : " + testInput);
        return "view";
    }

    @PostMapping(value = "/")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile img) throws IOException {

        model.addAttribute("filepath", img.getOriginalFilename());
        model.addAttribute("encoded", Base64.getEncoder().encodeToString(img.getBytes()));
        System.out.println("Image Upload : " + img.getOriginalFilename());
        return "view";
    }
}
