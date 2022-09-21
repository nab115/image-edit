package core.crop;
import java.awt.image.BufferedImage;

public class Core {
    public static BufferedImage crop(
                    BufferedImage img
                    , int x1
                    , int y1
                    , int x2
                    , int y2
                    ) {

        int height = img.getHeight();
        int width = img.getWidth();
        int w = x2 - x1;
        int h = y2 - y1;

        return img.getSubimage(x1, y1, w, h);
    }
}