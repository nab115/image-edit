package com.example.imageprocessing.controllers;

import com.example.imageprocessing.components.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;

@Controller
public class TestController {

    @Autowired
    Image image;

    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submit(@RequestBody String testInput) {

        System.out.println("Test Controller Working : " + testInput);
        image.increment();
        System.out.println(image.getCount());
        return "view";
    }
}
