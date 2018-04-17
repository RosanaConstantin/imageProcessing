package utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.awt.Color;

public class matrix {
		
	public static int[][] pixels( int x, int y, Image image){
		PixelReader pixelReader = image.getPixelReader();
		int[][] gray = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				gray[i][j] = (int) Luminance.lum(new Color(pixelReader.getArgb(x+i-1, y+j-1)));
			}
		}
		return gray;
	}
	
}
