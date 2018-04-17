package utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Rosana-Constantin on 15-Apr-18.
 */

public class PrewittVertical {

    public static int truncate(int a) {
        if      (a <   0) return 0;
        else if (a > 255) return 255;
        else              return a;
    }

    public static Image PrewittVertical(Image image, ProgressIndicator progressIndicator){
        int width;
        int height;

        int[][] filter = {
                { -1,  0,  1 },
                { -1,  0,  1 },
                { -1,  0,  1 }
        };
        width = (int) Math.round(image.getWidth());
        height = (int) Math.round(image.getHeight());

        BufferedImage mimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        progressIndicator.setProgress(Double.valueOf(40));

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {

                int[][] gray = matrix.pixels(x, y, image);

                // apply filter
                int gray1 = pixel.getGray1(filter, gray);
                int gray2 = pixel.getGray2(filter, gray);

                int magnitude =truncate((int) Math.sqrt(gray1*gray1 + gray2*gray2));
                Color grayscale = new Color(magnitude, magnitude, magnitude);
                mimage.setRGB(x, y,grayscale.getRGB());
            }
        }
        Image transformImg = SwingFXUtils.toFXImage(mimage, null);
        progressIndicator.setProgress(Double.valueOf(80));
        return transformImg;
    }
}
