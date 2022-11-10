package com.example.imageprocessing.components;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class Image {

    private BufferedImage original;
    private BufferedImage transformed;
    private int count;

    public Image() {
        count = 0;
        System.out.println("Initialized Image Bean");
    }

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
