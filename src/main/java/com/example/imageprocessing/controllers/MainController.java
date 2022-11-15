package com.example.imageprocessing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;

import com.example.imageprocessing.components.Image;

@Controller
public class MainController {

    @Autowired
    Image image;

    @GetMapping("/")
    public String root() {
        return "initial";
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String transform(Model model, @RequestBody String input){
        String command = input.split("=")[0];
        System.out.println("Transform : " + command);
        try {
            image.convert(command);
            model.addAttribute("encoded", image.getBase64Encoded());
        } catch (IOException e) {
            // do nothing
        }

        return "upload";

    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(Model model, @RequestParam("image") MultipartFile img) throws IOException {

        System.out.println("Image Upload : " + img.getOriginalFilename());

        image.setImage(ImageIO.read(img.getInputStream()));

        model.addAttribute("encoded", image.getBase64Encoded());

        return "upload";
    }
}
