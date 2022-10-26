package com.example.imageprocessing.components;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUp implements CommandLineRunner {

    public StartUp() {
    }

    @Override
    public void run(String[] args) {
        System.out.println("Starting Application . . .");
    }
}
