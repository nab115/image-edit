import java.lang.Exception;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;

import helper.Helper;

class imageProcessing {
    public static void main(String args[]) throws Exception {
        if (args.length != 2) {
            System.out.println(args.length);
            throw new Exception("imageProcessing requires exactly 2 arguments");
        }

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(args[0]));
        } catch (IOException e) {
            throw new IOException(String.format("image [" 
            + args[0] 
            + "] not found"));
        }
        String trunc[] = args[1].split(",")
        BufferedImage result = Helper.crop(img, trunc);
    }
}