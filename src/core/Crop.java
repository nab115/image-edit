package core;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Crop {

    public static BufferedImage crop(BufferedImage img, String[] edges) throws Exception{

        // check size is 4
        if (edges.length != 4) {
            throw new Exception( "crop input array must be length 4");
        }

        // transform edges elements to integers
        Integer[] integer_edges = convert_to_integer_array(edges);
        // crop image
        return crop(
                img
                , integer_edges[0]
                , integer_edges[1]
                , integer_edges[2]
                , integer_edges[3]
        );
    }
    private static BufferedImage crop(BufferedImage img, int x1, int y1, int x2, int y2) {

        int height = img.getHeight();
        int width = img.getWidth();
        int w = x2 - x1;
        int h = y2 - y1;

        return img.getSubimage(x1, y1, w, h);
    }

    private static Integer[] convert_to_integer_array(String[] stringArray) {
        Integer[] intArray = new Integer[stringArray.length];

        for(int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }
        return intArray;
    }
}