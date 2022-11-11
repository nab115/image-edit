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
    private boolean imageSet = false;

    public void setImage(BufferedImage bi) {
        original = bi;
        transformed = bi;
        imageSet = true;
    }

    public String getBase64Encoded() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(transformed, "JPG", baos);
        String encoded = Base64.getEncoder().encodeToString(baos.toByteArray());
        baos.close();
        return encoded;
    }

    public void greyscale() {
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

    public void crop() throws IOException {
        transformed = transformed.getSubimage(0, 0, 400, 400);
        File outputfile = new File("cropped.jpg");
        ImageIO.write(transformed, "jpg", outputfile);
    }

    public BufferedImage getImage(){
        return transformed;
    }

    public boolean hasData(){
        return imageSet;
    }


}
