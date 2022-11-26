package com.example.imageedit.components;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Component
public class Image {

    private byte[] imageData;
    private BufferedImage image;
    private boolean imageSet = false;

    public void setImage(byte[] imageData) {

        this.imageData = imageData;
        try {
            reset();
            imageSet = true;
        } catch (IOException e) {
            System.out.println("Image has not been uploaded");
        }
    }

    public void clearImage() {
        this.imageData = null;
        this.image = null;
        this.imageSet = false;
    }

    public String getBase64Encoded() throws IOException {

        if (!imageSet) {
            System.out.println("Image has not been uploaded");
            throw new IOException();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "JPG", baos);
        String encoded = Base64.getEncoder().encodeToString(baos.toByteArray());
        baos.close();
        return encoded;
    }

    public void convert(String command, String cropBounds) throws IOException, IllegalArgumentException {

        if (!imageSet) {
            System.out.println("Image has not been uploaded");
            throw new IOException();
        }

        if (command.equals("greyscale")) {
            this.greyscale();
        } else if (command.equals("crop")) {
            this.crop(cropBounds);
        } else if (command.equals("reset")) {
            this.reset();
        } else throw new IllegalArgumentException(command + " is not a valid processing feature");
    }

    private void reset() throws IOException {

        ByteArrayInputStream bias = new ByteArrayInputStream(this.imageData);
        this.image = ImageIO.read(bias);
    }

    private void greyscale() {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int r, g, b, rgb, grey;
                rgb = image.getRGB(i, j);
                r = (rgb >> 16) & 0xFF;
                g = (rgb >> 8) & 0xFF;
                b = rgb & 0xFF;
                grey = (r + g + b) / 3;
                grey = (grey << 16) + (grey << 8) + grey;
                image.setRGB(i, j, grey);
            }
        }
    }

    private void crop(String cropParams) throws IOException {
        String[] bounds = cropParams.split(",");
        try {
            this.crop(
                    Double.parseDouble(bounds[0])
                    , Double.parseDouble(bounds[1])
                    , Double.parseDouble(bounds[2])
                    , Double.parseDouble(bounds[3])
                    , Double.parseDouble(bounds[4])
                    , Double.parseDouble(bounds[5])
            );
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void crop(double l, double t, double r, double b, double width, double height) throws IOException {

        int x = (int) (image.getWidth() * (l / width));
        int y = (int) (image.getHeight() * (t / height));
        int x2 = (int) (image.getWidth() * (r / width));
        int y2 = (int) (image.getHeight() * (b / height));
        image = image.getSubimage(x, y, x2 - x, y2 - y);
    }

    // reference : https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
    public BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
