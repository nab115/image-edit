package com.example.imageprocessing.components;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Component
public class Image {

    private BufferedImage original;
    private BufferedImage transformed;

    public void setImage(BufferedImage bi) {
        original = bi;
        transformed = bi;
    }

    public String getBase64Encoded() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(transformed, "JPG", baos);
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
