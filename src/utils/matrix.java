package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class matrix {
		
	public static int[][] pixels( int x, int y, BufferedImage image){
		int[][] gray = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				gray[i][j] = (int) Luminance.lum(new Color(image.getRGB(x+i-1, y+j-1)));
			}
		}
		return gray;
	}
	
}
