import java.lang.Exception;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import core.Crop;

class imageProcessing {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println(args.length);
            throw new Exception("imageProcessing requires exactly 2 arguments");
        }

        BufferedImage img;
        try {
            img = ImageIO.read(new File(args[0]));
        } catch (IOException e) {
            throw new IOException(String.format("image ["
            + args[0]
            + "] not found"));
        }
        String[] edges = args[1].split(",");
        BufferedImage result = Crop.crop(img, edges);

        File outputFile = new File("image.jpg");
        ImageIO.write(result, "jpg", outputFile);
    }
}