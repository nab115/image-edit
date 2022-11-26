package com.example.imageedit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.example.imageedit.components.Image;

@Controller
public class MainController {

    @Autowired
    Image image;

    @GetMapping("/")
    public String root() {
        image.clearImage();
        return "upload";
    }

    @GetMapping("/image")
    @ResponseBody
    public String getImage() {

        try {
            return image.getBase64Encoded();
        } catch (IOException e) {
            return null;
        }
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public String transform(
            @RequestParam("transform") String transform
            , @RequestParam(value="cropParams", required = false) String cropParams
    ) throws UnsupportedEncodingException {

        cropParams = cropParams == null ? null : URLDecoder.decode(cropParams, "UTF-8");

        System.out.println("Transform : " + transform);

        try {
            image.convert(transform, cropParams);
            return image.getBase64Encoded();
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "error";
        }

    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(Model model, @RequestParam("image") MultipartFile img) throws IOException {

        System.out.println("Image Upload : " + img.getOriginalFilename());

        image.setImage(img.getBytes());

        model.addAttribute("encoded", image.getBase64Encoded());

        return "upload";
    }
}
