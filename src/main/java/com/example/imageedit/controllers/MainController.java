package com.example.imageedit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;

import com.example.imageedit.components.Image;

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
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "initial";
        }

        return "upload";

    }

    @PostMapping(value = "/crop", consumes = "text/plain")
    @ResponseBody
    public String setCrop(@RequestBody String input) throws IOException {
        String[] bounds = input.split(",");
        try {
            image.setCrop(
                    Double.parseDouble(bounds[0])
                    , Double.parseDouble(bounds[1])
                    , Double.parseDouble(bounds[2])
                    , Double.parseDouble(bounds[3])
                    , Double.parseDouble(bounds[4])
                    , Double.parseDouble(bounds[5])
            );
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return "success";
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(Model model, @RequestParam("image") MultipartFile img) throws IOException {

        System.out.println("Image Upload : " + img.getOriginalFilename());

        image.setImage(ImageIO.read(img.getInputStream()));

        model.addAttribute("encoded", image.getBase64Encoded());

        return "upload";
    }
}
