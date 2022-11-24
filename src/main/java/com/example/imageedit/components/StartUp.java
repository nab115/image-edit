package com.example.imageedit.components;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUp implements CommandLineRunner {

    @Override
    public void run(String[] args) {
        System.out.println("Starting Application . . .");
    }
}
