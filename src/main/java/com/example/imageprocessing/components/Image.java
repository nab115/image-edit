package com.example.imageprocessing.components;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Component
public class Image {

    private BufferedImage original;
    private BufferedImage transformed;
    private BufferedImage cropped;
    private boolean imageSet = false;

    public void setImage(BufferedImage bi) {
        original = bi;
        transformed = bi;
        cropped = bi;
        imageSet = true;
    }

    public String getBase64Encoded() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(transformed, "JPG", baos);
        String encoded = Base64.getEncoder().encodeToString(baos.toByteArray());
        baos.close();
        return encoded;
    }

    public void convert(String command) throws IOException, IllegalArgumentException {

        if (!imageSet) {
            System.out.println("Image has not been uploaded");
            throw new IOException();
        }

        if (command.equals("greyscale")) {
            this.greyscale();
        } else if (command.equals("crop")) {
            this.crop();
        } else throw new IllegalArgumentException(command + " is not a valid processing feature");
    }

    private void greyscale() {
        for (int i = 0; i < transformed.getWidth(); i++) {
            for (int j = 0; j < transformed.getHeight(); j++) {
                int r, g, b, rgb, grey;
                rgb = transformed.getRGB(i, j);
                r = (rgb >> 16) & 0xFF;
                g = (rgb >> 8) & 0xFF;
                b = rgb & 0xFF;
                grey = (r + g + b) / 3;
                grey = (grey << 16) + (grey << 8) + grey;
                transformed.setRGB(i, j, grey);
            }
        }
    }

    private void crop() throws IOException {
        transformed = cropped;
    }

    public void setCrop(double l, double t, double r, double b, double width, double height) throws IOException {

        int x = (int) (transformed.getWidth() * (l / width));
        int y = (int) (transformed.getHeight() * (t / height));
        int x2 = (int) (transformed.getWidth() * (r / width));
        int y2 = (int) (transformed.getHeight() * (b / height));
        System.out.println(transformed.getWidth() + " " + transformed.getHeight());
        System.out.println(l + " " + t + " " + r + " " + b + " " + height);
        System.out.println(x + " " + y + " " + x2 + " " + y2);
        cropped = transformed.getSubimage(x, y, x2 - x, y2 - y);
        System.out.println("cropped image has been set");
    }

    public BufferedImage getImage(){
        return transformed;
    }

    public boolean hasData(){
        return imageSet;
    }


}
